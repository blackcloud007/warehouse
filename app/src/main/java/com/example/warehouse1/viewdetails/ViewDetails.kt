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
import com.loopj.android.http.AsyncHttpClient
import jxl.Sheet
import jxl.Workbook
import org.eazegraph.lib.charts.BarChart
import org.eazegraph.lib.models.BarModel
import java.io.InputStream
import java.lang.StringBuilder
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

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

        try {
            utilFun()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        order(map)
        adapter= LotAdapter(this,list,this)
        rv.layoutManager= LinearLayoutManager(this)
        rv.adapter=adapter
    }
    @Throws(SQLException::class)
    private fun utilFun() {
        Thread {
            val records = StringBuilder()
            try {
                Class.forName("com.mysql.jdbc.Driver")
                val connection = DriverManager.getConnection(
                    url,
                    username,
                    password
                )
                val statement = connection.createStatement()
                val rs: ResultSet = statement.executeQuery("Select * FROM $TABLE_NAME ORDER BY CURRENTDATE DESC,CURRENTTIME DESC LIMIT 100")
                while (rs.next()){
                    if(map[rs.getString(1)]==null)
                        map[rs.getString(1)]= mutableListOf(mutableListOf<String>(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)," "+rs.getString(8),rs.getString(6),rs.getString(7)))
                    else map[rs.getString(1)]?.add((mutableListOf<String>(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)," "+rs.getString(8),rs.getString(6),rs.getString(7))))

                    records.append("NODEID: ").append(rs.getString(1))
                        .append(",TGS2620 ").append(rs.getString(2))
                        .append(",TGS2602 ").append(rs.getString(3))
                        .append(",TGS2600 ").append(rs.getString(4))
                        .append(",TGS822 ").append(rs.getString(5))
                        .append(",CURRENTDATE ").append(rs.getString(6))
                        .append(",CURRENTTIME ").append(rs.getString(7))
                        .append(",STATUS ").append(rs.getString(8))
                        .append("\n")
                }
                for (key in map.keys) {
                    AsyncHttpClient.log.d("NODEE","\n$key----->"+ map[key].toString())
                }
                AsyncHttpClient.log.d("MAP SIZE", map.size.toString())
                connection.close()

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }.start()

    }
    private fun order(map: MutableMap<String, MutableList<MutableList<String>>>) {
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
        intent.putExtra("map",map)
        startActivity(intent)
         }

    companion object {
        private const val DATABASE_NAME = "PROJECT"
        const val url =
            "jdbc:mysql://nwarehouse.cmwq7a5jjeyz.ap-south-1.rds.amazonaws.com:3306/$DATABASE_NAME"
        const val username = "admin"
        const val password = "project123"
        const val TABLE_NAME = "TEST4"
        val map: HashMap<String,MutableList<MutableList<String>>> = hashMapOf<String,MutableList<MutableList<String>>>()

    }
}
