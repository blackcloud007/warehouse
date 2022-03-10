package com.example.warehouse1.viewdetails.Node
import android.content.Intent
import android.os.Bundle
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouse1.R
import com.example.warehouse1.viewdetails.LotModel
import com.example.warehouse1.viewdetails.RawData.Node

class Lot : AppCompatActivity(), NodeAdapter.OnTabListener {
    private lateinit var rv: RecyclerView
    var adapter: NodeAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lot)
        rv= findViewById(R.id.lotrv)

        val t: LotModel = intent.getSerializableExtra("t") as LotModel
        map =intent.getSerializableExtra("map") as HashMap<String,MutableList<MutableList<String>>>
        list=intent.getSerializableExtra("nodelist")as ArrayList<NodeModel>

        order(t,map)

        adapter= NodeAdapter(this,list,this)
        rv.layoutManager= GridLayoutManager(this,2)
        rv.adapter=adapter
    }
    private fun order(t: LotModel, map: HashMap<String, MutableList<MutableList<String>>>){
        findViewById<TextView>(R.id.tv_nodelot_name).text = "Lot:${t.name}"
        findViewById<TextView>(R.id.tv_node_avg_Q).text=t.quality
        findViewById<TextView>(R.id.tv_node_avgs1).text=t.s1.toString()
        findViewById<TextView>(R.id.tv_node_avgs2).text=t.s2.toString()
        findViewById<TextView>(R.id.tv_node_avgs3).text=t.s3.toString()
        findViewById<TextView>(R.id.tv_node_avgs4).text=t.s4.toString()
        }

    override fun onClick(position: Int) {
        val intent = Intent(applicationContext, Node::class.java)
        val tt: NodeModel = list[position]
            intent.putExtra("tt", tt)
        intent.putExtra("map",map)
        startActivity(intent)
    }

companion object {
    var map: HashMap<String,MutableList<MutableList<String>>> = hashMapOf<String,MutableList<MutableList<String>>>()
    var list:ArrayList<NodeModel> = ArrayList<NodeModel>()
}
}