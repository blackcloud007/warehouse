package com.example.warehouse1.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouse1.R
import com.example.warehouse1.about.About
import com.example.warehouse1.profile.Profile
import com.example.warehouse1.temperature.Temperature
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
        myLists!!.add(DashBoardModel(R.drawable.viewdetails,"View Details"))
        myLists!!.add(DashBoardModel(R.drawable.profile,"Profile"))
        myLists!!.add(DashBoardModel(R.drawable.code_android_logo,"About"))
        myLists!!.add(DashBoardModel(R.drawable.temp,"Temperature and Humidity"))
        myLists!!.add(DashBoardModel(R.drawable.logout,"Logout"))

        adapter = DashBoardAdapter(myLists!!, this, onTabListener = this)
        rv!!.adapter = adapter
    }

    override fun onClick(position: Int) {
        when(myLists!![position].img){
            R.drawable.viewdetails -> {
                startActivity(Intent(applicationContext, ViewDetails::class.java))
            }
            R.drawable.temp ->{
                startActivity(Intent(applicationContext,Temperature::class.java))
            }
            R.drawable.logout -> {
                Toast.makeText(this,"Logging out!!",Toast.LENGTH_SHORT).show()
                finish()
            }
            R.drawable.code_android_logo->startActivity(Intent(applicationContext, About::class.java))
            R.drawable.profile->startActivity(Intent(applicationContext, Profile::class.java))

            else -> Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show()
        }
    }
}