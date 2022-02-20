package com.example.warehouse1
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jxl.Sheet
import jxl.Workbook
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import java.io.InputStream

class MainActivity : AppCompatActivity() ,Adapter.OnTabListener {
    private lateinit var pieChart: PieChart
    private val list = mutableListOf<Item>()
    private lateinit var rv:RecyclerView

    var adapter:Adapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pieChart = findViewById(R.id.piechart)
        rv= findViewById(R.id.listOfData)


        order()
        pieChart.addPieSlice(
            PieModel(
                "", 100.toFloat(),
                Color.parseColor("#FFB8B8B8")
            )
        )

        adapter=Adapter(this,list,this)
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
                    val good:Int=s.getCell(1,i).contents.toInt()
                val moderate:Int=s.getCell(2,i).contents.toInt()
                val bad:Int=s.getCell(3,i).contents.toInt()
                list.add(Item(name,good,moderate,bad))

            }

        }catch(e:Exception){e.printStackTrace();}

    }
    private fun setData(g:Int, m:Int, b:Int) {
        findViewById<TextView>(R.id.tvG).text = g.toString()
        findViewById<TextView>(R.id.tvM).text = m.toString()
        findViewById<TextView>(R.id.tvB).text = b.toString()

        // Set the data and color to the pie chart
       pieChart.clearChart()
        pieChart.addPieSlice(
            PieModel(
                "Good", findViewById<TextView>(R.id.tvG).text.toString().toInt().toFloat(),
                Color.parseColor("#4CAF50")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Moderate",findViewById<TextView>(R.id.tvM).text.toString().toInt().toFloat(),
                Color.parseColor("#2196F3")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Bad", findViewById<TextView>(R.id.tvB).text.toString().toInt().toFloat(),
                Color.parseColor("#F44336")
            )
        )
        pieChart.startAnimation()
    }

    override fun onClick(position: Int) {
        setData(list[position].g, list[position].m, list[position].b)
    }
}
