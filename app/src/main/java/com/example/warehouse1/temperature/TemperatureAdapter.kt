package com.example.warehouse1.temperature

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouse1.R


class TemperatureAdapter(
    context: Context,
    listNode: List<TemperatureModel>,
    temperature: Temperature
): RecyclerView.Adapter<TemperatureAdapter.ViewHolder>() {
    var inflator: LayoutInflater = LayoutInflater.from(context)
    var list: List<TemperatureModel> = listNode
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TemperatureAdapter.ViewHolder {
        val view = inflator.inflate(R.layout.card_temperature_layout, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: TemperatureAdapter.ViewHolder, position: Int) {
        holder.name.text= list[position].nodeid
        holder.hum.text= list[position].humidity.toString()
        holder.temperature.text=list[position].temperature.toString()
        holder.date.text=list[position].currentdate.toString()
        holder.time.text=list[position].currenttime.toString()
    }
    override fun getItemCount(): Int {
        return list.size
    }
    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.findViewById(R.id.tv_temp_nodeid)
        var hum: TextView = itemView.findViewById(R.id.tv_humidity)
        var temperature: TextView =itemView.findViewById(R.id.tv_temperature)
        var date: TextView = itemView.findViewById(R.id.tv_tdate)
        var time: TextView = itemView.findViewById(R.id.tv_ttime)
    }


}