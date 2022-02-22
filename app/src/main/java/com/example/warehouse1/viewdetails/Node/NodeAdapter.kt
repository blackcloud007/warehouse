package com.example.warehouse1.viewdetails.Node

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouse1.R

class NodeAdapter(context: Context?, l: List<NodeModel>, onTabListener: OnTabListener) : RecyclerView.Adapter<NodeAdapter.ViewHolder>() {
    private var inflator: LayoutInflater = LayoutInflater.from(context)
    var list: List<NodeModel> = l
    private val onTabListener: OnTabListener = onTabListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflator.inflate(R.layout.card_node_layout, parent, false)
        return ViewHolder(view, onTabListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = list[position].node_num
        holder.name.text = name
        val s1 = list[position].s1.toString()
        holder.s1.text = s1
        val s2 = list[position].s2.toString()
        holder.s2.text = s2
        val s3 = list[position].s3.toString()
        holder.s3.text = s3
        val s4 = list[position].s4.toString()
        holder.s4.text = s4
        val quality = list[position].quality
        holder.quality.text = quality
    }

    inner class ViewHolder(itemView: View, onTabListener: OnTabListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var name: TextView = itemView.findViewById(R.id.tv_node_num)
        var s1: TextView = itemView.findViewById(R.id.tv_node_s1)
        var s2: TextView =itemView.findViewById(R.id.tv_node_s2)
        var s3: TextView = itemView.findViewById(R.id.tv_node_s3)
        var s4: TextView = itemView.findViewById(R.id.tv_node_s4)
        var quality: TextView = itemView.findViewById(R.id.tv_node_Q)
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