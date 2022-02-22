package com.example.warehouse1.dashboard

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.example.warehouse1.R

class DashBoardAdapter(private val myListList: List<DashBoardModel>, private val ct: Context, onTabListener: OnTabListener) :
    RecyclerView.Adapter<DashBoardAdapter.ViewHolder>() {
    private val onTabListener: OnTabListener = onTabListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_dashboard_layout, parent, false)
        return ViewHolder(view, onTabListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (img) = myListList[position]
        holder.imageView.setImageDrawable(ct.resources.getDrawable(img))
    }

    override fun getItemCount(): Int {
        return myListList.size
    }

    inner class ViewHolder(itemView: View,onTabListener:OnTabListener) : RecyclerView.ViewHolder(itemView) ,View.OnClickListener {
        val imageView: ImageView

        init {
            imageView = itemView.findViewById<View>(R.id.myimage) as ImageView
        }
        var onTabListener:OnTabListener = onTabListener
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