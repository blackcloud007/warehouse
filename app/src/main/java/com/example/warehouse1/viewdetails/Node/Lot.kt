package com.example.warehouse1.viewdetails.Node
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouse1.R
import com.example.warehouse1.viewdetails.LotModel
import com.example.warehouse1.viewdetails.RawData.Node

class Lot : AppCompatActivity(), NodeAdapter.OnTabListener {
    val list = mutableListOf<NodeModel>()
    private lateinit var rv: RecyclerView
    var adapter: NodeAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lot)
        rv= findViewById(R.id.lotrv)

        val t: LotModel = intent.getSerializableExtra("t") as LotModel
        map =intent.getSerializableExtra("map") as HashMap<String,MutableList<MutableList<String>>>
        order(t,map)

        adapter= NodeAdapter(this,list,this)
        rv.layoutManager= LinearLayoutManager(this)
        rv.adapter=adapter
    }
    private fun order(t: LotModel, map: HashMap<String, MutableList<MutableList<String>>>){
        findViewById<TextView>(R.id.tv_nodelot_name).text = t.name
        findViewById<TextView>(R.id.tv_node_avg_Q).text=t.quality
        findViewById<TextView>(R.id.tv_node_avgs1).text=t.s1.toString()
        findViewById<TextView>(R.id.tv_node_avgs2).text=t.s2.toString()
        findViewById<TextView>(R.id.tv_node_avgs3).text=t.s3.toString()
        findViewById<TextView>(R.id.tv_node_avgs4).text=t.s4.toString()
            var good:Int=0
            var moderate:Int=0
            var bad:Int=0
            try{
                var s1:Int=0
                var s2:Int=0
                var s3:Int=0
                var s4:Int=0
                var quality:String="NULL"
                for(i in map.keys){
                    var t=1
                    for(j in map[i]!!){
                        s1+=(j[0].toInt()-s1)/t
                        s2+=(j[1].toInt()-s2)/t
                        s3+=(j[2].toInt()-s3)/t
                        s4+=(j[3].toInt()-s4)/t
                        t+=1
                        when (j[4].trim()){
                            "Good" ->good+=1
                            "Not Good" ->moderate+=1
                            "Bad" -> bad+=1
                        }
                    }
                    if(good>moderate ){
                        quality = if(good>bad)
                            "Good"
                        else
                            "Bad"
                    }
                    else if(moderate>bad)
                            quality="Moderate"

                    list.add(NodeModel(i, s1, s2, s3, s4, quality))
                }

            }catch(e:Exception){e.printStackTrace()
               }
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

}
}