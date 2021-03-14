package com.example.a3andm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private val url = "http://barcelonaapi.marcpous.com/"
    val BusList = ArrayList<Streetname>()
    val Bus = ArrayList<String>()
    val lat = ArrayList<String>()
    val lon = ArrayList<String>()

    companion object {
        var BusListFull = ArrayList<MyData>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) 
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val service = retrofit.create(Interface::class.java)   
        val courseRequest = service.BusList()
        courseRequest.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                val allData = response.body()
                if (allData != null) {
                    for (c in allData.data.tmbs){
                        BusList.plusAssign(Streetname(c.street_name, c.buses))
                        Bus.add(c.street_name)
                        lat.add(c.lat)
                        lon.add(c.lon)
                    }
                    BusListFull = allData.data.tmbs as ArrayList<MyData>
                }
                myRecyclerView.adapter = Adapter(BusList)
                myRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                myRecyclerView.setHasFixedSize(true)
            }
            override fun onFailure(call: Call<Data>, t: Throwable) {
                println(t)
            }
        })
    }

    fun goToMap(view: View) {
        val intent3 = Intent(view.context, MapsActivity::class.java)
        intent3.putExtra("bus", Bus)
        intent3.putExtra("lat", lat)
        intent3.putExtra("lon", lon)
        view.context.startActivity(intent3)
    }
}

