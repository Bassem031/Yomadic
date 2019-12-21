package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.FontsContract
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_result.view.*
import okhttp3.*
import org.w3c.dom.Text
import java.io.IOException

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val dest = intent?.getStringExtra("destination")
        val bt = findViewById<Button>(R.id.bt)
        var list = fetchJson(dest)
        val i = Intent()
        i.putExtra("list" , list)

        bt.setOnClickListener{ v: View? ->
            setResult(RESULT_OK , i)
            finish()
        }

    }


    fun fetchJson(destination: String?): ArrayList<String> {


        val list = arrayListOf<String>()
        val nom = findViewById<TextView>(R.id.name)
        val category = findViewById<TextView>(R.id.category)
        val latitude = findViewById<TextView>(R.id.lat)
        val longitude = findViewById<TextView>(R.id.lng)
        val city = findViewById<TextView>(R.id.city)


      // var cha = ""


        val client = OkHttpClient()
        val url =
            "https://api.foursquare.com/v2/venues/explore?client_id=5ZPHRKEVEL0Q40GUC5XPQSED0VC0CR1BSM5G2RXKFDX2OKKA&client_secret=WYAXSUPXAHMLUCN4KMK30OJTMFGRLMRHLPHLVMR1QBV1CYUB&v=20191110&near=$destination"
        val request = Request.Builder().url(url).build()




        val res = client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()

            }

            override fun onResponse(call: Call, response: Response) {

                val ch = response?.body?.string()

                val gson = GsonBuilder().create()
                val homefeed = gson.fromJson(ch, HomeFeed::class.java)


                runOnUiThread( Runnable() {

                    val name = homefeed.response.groups[0].items[0].venue.name
                    list.add(name)
                    nom.text = name

                    val cat = homefeed.response.groups[0].items[0].venue.categories[0].name
                    list.add(cat)
                    category.text = cat

                    val cit = homefeed.response.groups[0].items[0].venue.location.city
                    list.add(cit)
                    city.text = cit

                    val lat = homefeed.response.groups[0].items[0].venue.location.lat.toString()
                    list.add(lat)
                    latitude.text = lat

                    val lng = homefeed.response.groups[0].items[0].venue.location.lng.toString()
                    list.add(lng)
                    longitude.text = lng

                } )
/*
                val ch2 = homefeed.response.groups[0].items[0].venue.name
                 Log.d("name" , ch2 )*/
                /*  val test = findViewById<TextView>(R.id.t1)
                  test.text = "$ch" */
            }
        })

        return list
    }
}

class HomeFeed(val response:Resp)
class Resp(val groups: List<Group> )
class Group( val items:List<Item>)
class Item(val venue:Venue)
class Venue(val name :String , val location:Location,val categories:List<Category> )
class Location(val city:String ,val state:String,val country:String,val lat:Float ,val lng :Float)
class Category(val name:String,val icon : Icon)
class Icon(val prefix:String,val suffix:String)