package com.example.warehouse1.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouse1.R
import com.example.warehouse1.viewdetails.Adapter
import com.example.warehouse1.viewdetails.ViewDetails


class Dashboard : AppCompatActivity(), Adapter.OnTabListener {
    var myLists: MutableList<MyList>? = null
    var rv: RecyclerView? = null
    var adapter: MyAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_layout)
        rv = findViewById<View>(R.id.rec) as RecyclerView
        rv!!.setHasFixedSize(true)
        rv!!.layoutManager = GridLayoutManager(this, 2)
        myLists = ArrayList()
        getdata()
    }

    private fun getdata() {
        myLists!!.add(MyList(R.drawable.viewdetails))
        myLists!!.add(MyList(R.drawable.setting))
        myLists!!.add(MyList(R.drawable.support))
        myLists!!.add(MyList(R.drawable.profile))
        myLists!!.add(MyList(R.drawable.logout))
        myLists!!.add(MyList(R.drawable.feedback))
        myLists!!.add(MyList(R.drawable.setting))
        myLists!!.add(MyList(R.drawable.support))
        myLists!!.add(MyList(R.drawable.profile))
        myLists!!.add(MyList(R.drawable.logout))
        myLists!!.add(MyList(R.drawable.feedback))
        adapter = MyAdapter(myLists!!, this, onTabListener = this)
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
        }
    }
}