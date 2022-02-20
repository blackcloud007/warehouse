package com.example.warehouse1

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class Adapter(context: Context?, l: List<Item>, onTabListener: OnTabListener) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    var inflator: LayoutInflater = LayoutInflater.from(context)
    var list: List<Item> = l
    private val onTabListener: OnTabListener = onTabListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflator.inflate(R.layout.custom_layout, parent, false)
        return ViewHolder(view, onTabListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = list[position].name
        holder.name.text = name
        val good = list[position].g.toString()
        holder.good.text = good
        val moderate = list[position].m.toString()
        holder.moderate.text = moderate
        val bad = list[position].b.toString()
        holder.bad.text = bad
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View, onTabListener: OnTabListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var name: TextView = itemView.findViewById(R.id.tv_name)
        var good: TextView = itemView.findViewById(R.id.tvG)
        var moderate:TextView=itemView.findViewById(R.id.tvM)
        var bad: TextView = itemView.findViewById(R.id.tvB)
        var onTabListener: OnTabListener = onTabListener
        override fun onClick(view: View) {
            onTabListener.onClick(adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    interface OnTabListener {
        fun onClick(position: Int)
    }

}