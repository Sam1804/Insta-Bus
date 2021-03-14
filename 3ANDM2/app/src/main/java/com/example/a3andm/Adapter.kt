package com.example.a3andm

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bus_items.view.*
import java.util.ArrayList

class Adapter(private val List: List<Streetname>) : RecyclerView.Adapter<Adapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.bus_items, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount() = List.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = List[position]
        holder.textView.text = currentItem.street
        holder.textView2.text = currentItem.busline
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView : TextView = itemView.text_view_1
        val textView2 : TextView = itemView.text_view_6

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailsActivity::class.java)
                val position = adapterPosition
                intent.putExtra("Position", position)
                intent.putExtra("TEXT_VIEW",textView.text.toString())
                itemView.context.startActivity(intent)
            }
        }
    }
}


