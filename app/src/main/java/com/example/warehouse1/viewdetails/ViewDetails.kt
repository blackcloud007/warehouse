package com.example.warehouse1.viewdetails
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouse1.R
import com.example.warehouse1.viewdetails.Node.Lot
import jxl.Sheet
import jxl.Workbook
import org.eazegraph.lib.charts.BarChart
import org.eazegraph.lib.models.BarModel
import java.io.InputStream

class ViewDetails : AppCompatActivity(), LotAdapter.OnTabListener  {
    private lateinit var barChart: BarChart
    private val list = mutableListOf<LotModel>()
    private lateinit var rv:RecyclerView

    var adapter: LotAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewdetails)
        barChart = findViewById(R.id.barchart)
        rv= findViewById(R.id.listOfData)

        order()

        adapter= LotAdapter(this,list,this)
        rv.layoutManager= LinearLayoutManager(this)
        rv.adapter=adapter
    }

    private fun order(){
        var good:Int=0
        var moderate:Int=0
        var bad:Int=0
        try{
            val ii:InputStream  = assets.open("Lots.xls")

            val w :Workbook= Workbook.getWorkbook(ii)
            val s:Sheet=w.getSheet(0)
            val row: Int =s.rows

            for(i in 0 until row){
                    val name =s.getCell(0,i).contents.toString()
                    val s1:Int=s.getCell(1,i).contents.toInt()
                    val s2:Int=s.getCell(2,i).contents.toInt()
                    val s3:Int=s.getCell(3,i).contents.toInt()
                    val s4:Int=s.getCell(4,i).contents.toInt()
                    val quality:String=s.getCell(5,i).contents
                    list.add(LotModel(name,s1,s2,s3,s4,quality))
                    when(s.getCell(5,i).contents){
                        "Good" -> good += 1
                        "Moderate" -> moderate += 1
                        "Bad" -> bad += 1
                    }
            }
            setData(good,moderate,bad, lotcout = row)

        }catch(e:Exception){e.printStackTrace()

           }

    }
    private fun setData(good:Int,moderate:Int, bad:Int,lotcout:Int) {
        // Set the data and color to the pie chart
        findViewById<TextView>(R.id.lotcount).text = lotcout.toString()
       barChart.clearChart()
        barChart.addBar(
            BarModel(
                good.toFloat(),
                Color.parseColor("#4CAF50")
            )
        )
        barChart.addBar(
            BarModel(moderate.toFloat(),
                Color.parseColor("#2196F3")
            )
        )
        barChart.addBar(
            BarModel(bad.toFloat(),
                Color.parseColor("#F44336")
            )
        )

        barChart.startAnimation()
    }
    override fun onClick(position: Int) {

        val intent = Intent(applicationContext, Lot::class.java)
        val t: LotModel = LotModel(list[position].name,list[position].s1,list[position].s2,list[position].s3,list[position].s4,list[position].quality)
        intent.putExtra("t", t)
        startActivity(intent)
         }
}
