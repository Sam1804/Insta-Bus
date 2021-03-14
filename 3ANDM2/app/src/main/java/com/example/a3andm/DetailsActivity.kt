package com.example.a3andm

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bus_items.view.image_view
import kotlinx.android.synthetic.main.details_view.view.*
import okhttp3.internal.notify
import java.util.ArrayList

class DetailsActivity : AppCompatActivity() {

    var position = 0
    var PhotoName = ArrayList<PhotoName>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        position = intent.getIntExtra("Position",0)
        // Fonction pour rénitialiser le JSON
        //saveData()
        loadData()
        var PhotoNameV2 = ArrayList<PhotoName>()
        for (i in PhotoName){
            if(i.id == position)
                PhotoNameV2.add(i)
        }
        RecyclerViewV2.layoutManager = LinearLayoutManager(this)
        RecyclerViewV2.adapter = DetailsAdapter(PhotoNameV2)
        RecyclerViewV2.setHasFixedSize(true)
    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        var gson = Gson()
        var json = gson.toJson(PhotoName)
        editor.apply(){
            editor.putString("DetailsPhoto", json)
        }.apply()
    }

    private fun loadData(){
        var sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        var gson = Gson()
        var json = sharedPreferences.getString("DetailsPhoto", null)
        var type = object : TypeToken<ArrayList<PhotoName>>() {}.type
        if(json != null){
            PhotoName = gson.fromJson(json, type)
        }
    }

    private class DetailsAdapter (private val List: List<PhotoName>): RecyclerView.Adapter<DetailsViewHolder>(){

        override fun getItemCount() = List.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            val detailsView = layoutInflater.inflate(R.layout.details_view, parent, false)

            return DetailsViewHolder(detailsView)
        }

        override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
            val currentItem = List[position]

            holder.picture.setImageURI(Uri.parse(currentItem.url))
            holder.titre.text = currentItem.name
            holder.date.text = currentItem.date
        }
    }

    private class DetailsViewHolder (val detailsView: View): RecyclerView.ViewHolder(detailsView){
        val picture : ImageView = detailsView.image_view
        val titre : TextView = detailsView.text_view_3
        val date : TextView = detailsView.text_view_4
    }

    fun goToAnActivity(detailsView: View) {
        val intent2 = Intent(detailsView.context, PhotoActivity::class.java)
        intent2.putExtra("id", position)
        detailsView.context.startActivity(intent2)
    }

}