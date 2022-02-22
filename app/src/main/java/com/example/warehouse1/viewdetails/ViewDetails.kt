package com.example.warehouse1.viewdetails
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouse1.R
import jxl.Sheet
import jxl.Workbook
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.io.InputStream

class ViewDetails : AppCompatActivity() , Adapter.OnTabListener {
    private lateinit var pieChart: PieChart
    private val list = mutableListOf<Item>()
    private lateinit var rv:RecyclerView

    var adapter: Adapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_details)
        pieChart = findViewById(R.id.piechart)
        rv= findViewById(R.id.listOfData)


        order()
        pieChart.addPieSlice(
            PieModel(
                "", 100.toFloat(),
                Color.parseColor("#FFB8B8B8")
            )
        )

        adapter= Adapter(this,list,this)
        rv.layoutManager= LinearLayoutManager(this)
        rv.adapter=adapter
    }

    private fun order(){
        try{
            val ii:InputStream  = assets.open("Book21.xls")

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
                list.add(Item(name,s1,s2,s3,s4,quality))

            }

        }catch(e:Exception){e.printStackTrace();}

    }
    private fun setData(s1:Int, s2:Int, s3:Int,s4:Int,quality:String) {
        findViewById<TextView>(R.id.tv1).text = s1.toString()
        findViewById<TextView>(R.id.tv2).text = s2.toString()
        findViewById<TextView>(R.id.tv3).text = s3.toString()
        findViewById<TextView>(R.id.tv4).text = s4.toString()
        findViewById<TextView>(R.id.tvG).text = quality

        // Set the data and color to the pie chart
       pieChart.clearChart()
        pieChart.addPieSlice(
            PieModel(
                "Good", findViewById<TextView>(R.id.tv1).text.toString().toInt().toFloat(),
                Color.parseColor("#4CAF50")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Moderate",findViewById<TextView>(R.id.tv2).text.toString().toInt().toFloat(),
                Color.parseColor("#2196F3")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Bad", findViewById<TextView>(R.id.tv3).text.toString().toInt().toFloat(),
                Color.parseColor("#F44336")
            )
        )
        pieChart.startAnimation()
    }

    override fun onClick(position: Int) {
        setData(list[position].s1, list[position].s2, list[position].s3, list[position].s4, list[position].quality)
    }
}
