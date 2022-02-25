package com.example.warehouse1.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouse1.R
import com.example.warehouse1.viewdetails.ViewDetails


class Dashboard : AppCompatActivity(), DashBoardAdapter.OnTabListener {
    var myLists: MutableList<DashBoardModel>? = null
    var rv: RecyclerView? = null
    var adapter: DashBoardAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        rv = findViewById<View>(R.id.rec) as RecyclerView
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = GridLayoutManager(this, 2)
        myLists = ArrayList()
        getdata()
    }

    private fun getdata() {
        myLists!!.add(DashBoardModel(R.drawable.viewdetails))
        myLists!!.add(DashBoardModel(R.drawable.setting))
        myLists!!.add(DashBoardModel(R.drawable.support))
        myLists!!.add(DashBoardModel(R.drawable.profile))
        myLists!!.add(DashBoardModel(R.drawable.logout))
        myLists!!.add(DashBoardModel(R.drawable.feedback))
        myLists!!.add(DashBoardModel(R.drawable.setting))
        myLists!!.add(DashBoardModel(R.drawable.support))
        myLists!!.add(DashBoardModel(R.drawable.profile))
        myLists!!.add(DashBoardModel(R.drawable.logout))
        myLists!!.add(DashBoardModel(R.drawable.feedback))
        adapter = DashBoardAdapter(myLists!!, this, onTabListener = this)
        rv!!.adapter = adapter
    }

    override fun onClick(position: Int) {
        when(myLists!![position].img){
            R.drawable.viewdetails -> {
                startActivity(Intent(applicationContext, ViewDetails::class.java))
            }
            R.drawable.setting -> {
                Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show()
            }
            R.drawable.logout -> {
                Toast.makeText(this,"Logging out!!",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}