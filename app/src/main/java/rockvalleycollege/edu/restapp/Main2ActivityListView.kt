package rockvalleycollege.edu.restapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Main2ActivityListView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2_list_view)

        var restList = findViewById<TextView>(R.id.restList)
    }
}
