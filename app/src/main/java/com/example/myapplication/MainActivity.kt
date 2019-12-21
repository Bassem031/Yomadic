package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dest = findViewById<EditText>(R.id.d)
        val btn = findViewById<Button>(R.id.btn)

        // val ch =""
        btn.setOnClickListener {

            val  destination = dest.text.toString()
            //  Log.d("test " , destination )
            //val trst =""

            val i = Intent ( this , ResultActivity::class.java)

            i.putExtra("destination" , destination)

            startActivityForResult(i , 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            1 -> {
                when (resultCode) {
                    RESULT_OK -> {
                        val extras = data?.extras

                        val list = extras?.getStringArrayList("list")

                        val i = Intent ( this , MapsActivity::class.java)

                        i.putExtra("list" , list)

                        startActivity(i)
                    }
                    RESULT_CANCELED -> Toast.makeText(this , "action non valide", Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}
