package com.example.warehouse1.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouse1.R
import com.example.warehouse1.warehouseprofile.WarehouseProfile
import com.example.warehouse1.temperature.Temperature
import com.example.warehouse1.viewdetails.Warehouse
import com.google.android.material.appbar.AppBarLayout


class Dashboard : AppCompatActivity(), DashBoardAdapter.OnTabListener {
    var myLists: MutableList<DashBoardModel>? = null
    var rv: RecyclerView? = null
    var adapter: DashBoardAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val mToolbar:androidx.appcompat.widget.Toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        val mAppBarLayout : AppBarLayout = findViewById(R.id.appBar);

    rv = findViewById<View>(R.id.rec) as RecyclerView
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = GridLayoutManager(this, 2)
        myLists = ArrayList()
        getdata()
    }

    private fun getdata() {
        myLists!!.add(DashBoardModel(R.drawable.warehouse,"Warehouse"))
        myLists!!.add(DashBoardModel(R.drawable.profile,"Warehouse Profile"))
        myLists!!.add(DashBoardModel(R.drawable.viewdetails,"Detailed Report"))
        myLists!!.add(DashBoardModel(R.drawable.temp,"Temperature and Humidity"))
        myLists!!.add(DashBoardModel(R.drawable.logout,"Exit"))

        adapter = DashBoardAdapter(myLists!!, this, onTabListener = this)
        rv!!.adapter = adapter
    }

    override fun onClick(position: Int) {
        when(myLists!![position].img){
            R.drawable.warehouse -> {
                startActivity(Intent(applicationContext, Warehouse::class.java))
            }
            R.drawable.temp ->{
                startActivity(Intent(applicationContext,Temperature::class.java))
            }
            R.drawable.logout -> {
                finish()
            }
            R.drawable.profile->startActivity(Intent(applicationContext, WarehouseProfile::class.java))
            R.drawable.viewdetails->Intent(applicationContext, Warehouse::class.java)

            else -> Toast.makeText(this,"Coming Soon",Toast.LENGTH_SHORT).show()
        }
    }
}