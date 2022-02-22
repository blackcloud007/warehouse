package com.example.warehouse1.viewdetails.RawData

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouse1.R
import com.example.warehouse1.viewdetails.LotModel
import com.example.warehouse1.viewdetails.Node.NodeAdapter
import com.example.warehouse1.viewdetails.Node.NodeModel
import jxl.Sheet
import jxl.Workbook
import java.io.InputStream

class Node : AppCompatActivity() {
    val list = mutableListOf<RawModel>()
    private lateinit var rv: RecyclerView
    var adapter: RawAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lot)

            rv= findViewById(R.id.lotrv)
            val t: NodeModel = intent.getSerializableExtra("tt") as NodeModel
            order(t)
            adapter= RawAdapter(this,list)
            rv.layoutManager= LinearLayoutManager(this)
            rv.adapter=adapter
        }
        private fun order(t: NodeModel){
            findViewById<TextView>(R.id.tv_nodelot_name).text = t.node_num
            findViewById<TextView>(R.id.tv_node_avg_Q).text=t.quality
            findViewById<TextView>(R.id.tv_node_avgs1).text=t.s1.toString()
            findViewById<TextView>(R.id.tv_node_avgs2).text=t.s2.toString()
            findViewById<TextView>(R.id.tv_node_avgs3).text=t.s3.toString()
            findViewById<TextView>(R.id.tv_node_avgs4).text=t.s4.toString()
            var good:Int=0
            var moderate:Int=0
            var bad:Int=0
            try{
                val ii: InputStream = assets.open("RawData.xls")
                val w : Workbook = Workbook.getWorkbook(ii)
                val s: Sheet =w.getSheet(0)
                val row: Int =s.rows
                for(i in 0 until row){
                    val name =s.getCell(0,i).contents.toString()
                    val s1:Int=s.getCell(1,i).contents.toInt()
                    val s2:Int=s.getCell(2,i).contents.toInt()
                    val s3:Int=s.getCell(3,i).contents.toInt()
                    val s4:Int=s.getCell(4,i).contents.toInt()
                    val quality:String=s.getCell(5,i).contents
                    list.add(RawModel(name,s1,s2,s3,s4,quality))
                    when(s.getCell(5,i).contents){
                        "Good" -> good += 1
                        "Moderate" -> moderate += 1
                        "Bad" -> bad += 1
                    }
                }

            }catch(e:Exception){e.printStackTrace()
            }
        }

}