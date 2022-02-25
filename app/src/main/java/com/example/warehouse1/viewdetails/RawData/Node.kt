package com.example.warehouse1.viewdetails.RawData

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouse1.R
import com.example.warehouse1.viewdetails.Node.NodeModel
import com.loopj.android.http.AsyncHttpClient.log

class Node : AppCompatActivity() {
    val list = mutableListOf<RawModel>()
    private lateinit var rv: RecyclerView
    var adapter: RawAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lot)

            rv= findViewById(R.id.lotrv)
            val t: NodeModel = intent.getSerializableExtra("tt") as NodeModel
            map =intent.getSerializableExtra("map") as HashMap<String,MutableList<MutableList<String>>>

        order(t,map)
            adapter= RawAdapter(this,list)
            rv.layoutManager= LinearLayoutManager(this)
            rv.adapter=adapter
        }
        private fun order(t: NodeModel, map: HashMap<String, MutableList<MutableList<String>>>){
            findViewById<TextView>(R.id.tv_nodelot_name).text = t.node_id
            findViewById<TextView>(R.id.tv_node_avg_Q).text=t.quality
            findViewById<TextView>(R.id.tv_node_avgs1).text=t.s1.toString()
            findViewById<TextView>(R.id.tv_node_avgs2).text=t.s2.toString()
            findViewById<TextView>(R.id.tv_node_avgs3).text=t.s3.toString()
            findViewById<TextView>(R.id.tv_node_avgs4).text=t.s4.toString()
            try{
                for(i in map[t.node_id.trim()]!!){
                    val s1:Int=i[0].toInt()
                    val s2:Int=i[1].toInt()
                    val s3:Int=i[2].toInt()
                    val s4:Int=i[3].toInt()
                    val quality:String=i[4]
                    list.add(RawModel("${i[6]} (${i[5]})",s1,s2,s3,s4,quality))
                }

            }catch(e:Exception){e.printStackTrace()
            }
        }
    companion object {
        var map: HashMap<String,MutableList<MutableList<String>>> = hashMapOf<String,MutableList<MutableList<String>>>()
    }

}