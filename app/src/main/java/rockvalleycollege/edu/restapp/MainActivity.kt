package rockvalleycollege.edu.restapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        val pastRest = findViewById<Button>(R.id.pastRest)
        val restTitle = findViewById<EditText>(R.id.restTitle)
        var restRating = findViewById<EditText>(R.id.restRating)
        var restName = findViewById<EditText>(R.id.restName)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        var ref = FirebaseDatabase.getInstance().getReference("Message")
        var restList = findViewById<TextView>(R.id.restList)
        val btnHelp = findViewById<Button>(R.id.btnHelp)

        class Message(val id: String, val name: String, val message:String)

        btnSubmit.setOnClickListener {
            restName.requestFocus()
            var messageid = ref.push().key
            var messageg = Message(messageid, restName.text.toString(), restRating.text.toString())
            //hidekeyboard()
            restName.setText("")
            restRating.setText("")
            restName.requestFocus()
            ref.child(messageid).setValue(messageg).addOnCompleteListener {
                Toast.makeText(this, "Restaurant Added!",Toast.LENGTH_LONG) .show()

            }
        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                restList.text = ""
                val children = dataSnapshot.children
                children.forEach {
                    println("data: " + it.toString())
                    if (restList.text.toString() != "") {
                        restList.text = restList.text.toString() + "\n" + "Restaurant: " +
                                it.child("name").value.toString() + " " + "Rating: " + it.child("message").value.toString()
                    } else {
                        restList.text = "My Restaurants"
                        restList.text = restList.text.toString() + "\n" + "Restaurant: " +
                                it.child("name").value.toString() + " " + "Rating: " + it.child("message").value.toString()
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Message", "Failed to read Value.", error.toException())
            }
        })

        btnHelp.setOnClickListener {
            Toast.makeText(this, "Enter restaurant name and give it a rating.", Toast.LENGTH_LONG).show()
        }





        myRef.setValue("Hello, World!")

        pastRest.setOnClickListener { it: View? ->
            val intent = Intent(this, Main2ActivityListView::class.java)
            startActivity(intent)
            fun hidekeyboard() {
                try {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    // TODO: handle exception

                }

            }
        }
    }
}