package com.example.warehouse1.viewdetails

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekn.gruzer.gaugelibrary.HalfGauge
import com.ekn.gruzer.gaugelibrary.Range
import com.example.warehouse1.R
import com.example.warehouse1.viewdetails.Node.Lot
import com.example.warehouse1.viewdetails.Node.NodeModel
import com.loopj.android.http.AsyncHttpClient
import jxl.Sheet
import jxl.Workbook
import org.eazegraph.lib.charts.BarChart
import org.eazegraph.lib.models.BarModel
import java.io.InputStream
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException


class Warehouse : AppCompatActivity(), LotAdapter.OnTabListener  {
    lateinit var context: Context
    private lateinit var barChart: BarChart
    private lateinit var rv:RecyclerView
    val listNode = ArrayList<NodeModel>()
    private val list = mutableListOf<LotModel>()
    lateinit var map: HashMap<String,MutableList<MutableList<String>>>
    var adapter: LotAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewdetails)
        barChart = findViewById(R.id.barchart)
        rv= findViewById(R.id.listOfData)
        context=this
        map= hashMapOf<String,MutableList<MutableList<String>>>()
        getDataFromServer().execute()

        findViewById<ImageButton>(R.id.bt_refresh).setOnClickListener{
            map.clear()
            listNode.clear()
            list.clear()
            getDataFromServer().execute()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // below line is to get our inflater
        val inflater = menuInflater

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu)

        // below line is to get our menu item.
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)

        // getting search view of our item.
        val searchView: SearchView = searchItem.getActionView() as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText)
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<LotModel> = ArrayList()

        // running a for loop to compare elements.
        for (item in list) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Lot Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter?.filterList(filteredlist)
        }
    }


    internal inner class getDataFromServer(): AsyncTask<Void,Void, HashMap<String, MutableList<MutableList<String>>>>() {
        lateinit var progressDialog: ProgressDialog
        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog=ProgressDialog(context)
            progressDialog.setMessage("Updating")
            progressDialog.setCancelable(false)
            progressDialog.show()
        }
        override fun doInBackground(vararg p0: Void?): HashMap<String, MutableList<MutableList<String>>> {
            return utilFun()
        }

        override fun onPostExecute(result: HashMap<String, MutableList<MutableList<String>>>?) {
            super.onPostExecute(result)
            progressDialog.dismiss()
            if (result != null) {
                order(result)
                adapter= LotAdapter(context,list,this@Warehouse)
                rv.layoutManager= GridLayoutManager(context,3)
                rv.adapter=adapter
            }
            else
                Toast.makeText(context,"Error in Loading",Toast.LENGTH_SHORT).show()
        }

    }

    @Throws(SQLException::class)
     fun utilFun(): HashMap<String, MutableList<MutableList<String>>> {
            //val records = StringBuilder()
            try {
                Class.forName("com.mysql.jdbc.Driver")
                val connection = DriverManager.getConnection(
                    url,
                    username,
                    password
                )
                val statement = connection.createStatement()
                val rs: ResultSet = statement.executeQuery("Select * FROM $TABLE_NAME ORDER BY CURRENTDATE DESC,CURRENTTIME DESC LIMIT 50")
                while (rs.next()){
                    if(map[rs.getString(1)]==null)
                        map[rs.getString(1)]= mutableListOf(mutableListOf<String>(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)," "+rs.getString(8),rs.getString(6),rs.getString(7)))
                    else map[rs.getString(1)]?.add((mutableListOf<String>(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)," "+rs.getString(8),rs.getString(6),rs.getString(7))))

                    //records.append("NODEID: ").append(rs.getString(1))
                    //    .append(",TGS2620 ").append(rs.getString(2))
                    //    .append(",TGS2602 ").append(rs.getString(3))
                    //    .append(",TGS2600 ").append(rs.getString(4))
                   //     .append(",TGS822 ").append(rs.getString(5))
                   //     .append(",CURRENTDATE ").append(rs.getString(6))
                   //     .append(",CURRENTTIME ").append(rs.getString(7))
                    //   .append(",STATUS ").append(rs.getString(8))
                   //     .append("\n")
                }
                for (key in map.keys) {
                    AsyncHttpClient.log.d("NODEE","\n$key----->"+ map[key].toString())
                }
                AsyncHttpClient.log.d("MAP SIZE", map.size.toString())
                connection.close()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        return map
    }
    private fun order(map: MutableMap<String, MutableList<MutableList<String>>>) {
        var good:Int=0
        var degraded:Int=0
        var start_to_degrade:Int=0
        var average:Int=0
        var s1:Int=0
        var s2:Int=0
        var s3:Int=0
        var s4:Int=0
        var quality:String="NULL"
        try{
            for(i in map.keys){
                s1=0
                s2=0
                s3=0
                s4=0
                good=0
                start_to_degrade=0
                average=0
                degraded=0
                quality="NULL"
                var t=1
                for(j in map[i]!!){
                    s1+=(j[0].toInt()-s1)/t
                    s2+=(j[1].toInt()-s2)/t
                    s3+=(j[2].toInt()-s3)/t
                    s4+=(j[3].toInt()-s4)/t
                    t+=1
                    when (j[4].trim()){
                        "Good" -> good += 1
                        "Degraded" -> degraded += 1
                        "Start to degrade" ->start_to_degrade+=1
                        "Average" -> average += 1
                    }
                }
                if(good>average && good>start_to_degrade && good >degraded)
                    quality="Good"
                else if(average>start_to_degrade && average>degraded)
                    quality="Average"
                else if(start_to_degrade>degraded)
                    quality="Start to degrade"
                else
                    quality="Degraded"
                listNode.add(NodeModel(i, s1, s2, s3, s4, quality))
            }
                s1=0
                s2=0
                s3=0
                s4=0
                quality="NULL"
                good=0
                average=0
                degraded=0
                var t=1
                for(node in listNode){
                    s1+=(node.s1-s1)/t
                    s2+=(node.s2.toInt()-s2)/t
                    s3+=(node.s3.toInt()-s3)/t
                    s4+=(node.s4.toInt()-s4)/t
                    t+=1
                    when (node.quality.trim()){
                        "Good" -> good += 1
                        "Degraded" -> degraded += 1
                        "Start to degrade" ->start_to_degrade+=1
                        "Average" -> average += 1
                    }
                }
            if(good>average && good>start_to_degrade && good >degraded)
                quality="Good"
            else if(average>start_to_degrade && average>degraded)
                quality="Average"
            else if(start_to_degrade>degraded)
                quality="Start to degrade"
            else
                quality="Degraded"
            list.add(LotModel("A",s1,s2,s3,s4,quality))
                } catch(e:Exception){e.printStackTrace()
        }
        try{
            good=1
            average=0
            degraded=0
            start_to_degrade=0
            val ii:InputStream  = assets.open("Lots.xls")
            val w :Workbook= Workbook.getWorkbook(ii)
            val s:Sheet=w.getSheet(0)
            val row: Int =s.rows
            setData(4,1,2,1, lotcout = row)
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
                        "Degraded" -> degraded += 1
                        "Start to degrade" ->start_to_degrade+=1
                        "Average" -> average += 1
                    }
            }

        }catch(e:Exception){e.printStackTrace()

           }

    }
    private fun setData(good:Int,Avg:Int, st:Int,deg:Int,lotcout:Int) {
        // Set the data and color to the pie chart
        findViewById<TextView>(R.id.lotcount).text = (lotcout).toString()
       barChart.clearChart()
        barChart.addBar(
            BarModel(
                good.toFloat(),
                Color.parseColor("#4CAF50")
            )
        )
        barChart.addBar(
            BarModel(Avg.toFloat(),
                Color.parseColor("#2196F3")
            )
        )
        barChart.addBar(
            BarModel(
                st.toFloat(),
                Color.parseColor("#FFEB3B")
            )
        )
        barChart.addBar(
            BarModel(deg.toFloat(),
                Color.parseColor("#F44336")
            )
        )

        barChart.startAnimation()
        val halfGauge=findViewById<HalfGauge>(R.id.halfGauge)
        val halfGauge1=findViewById<HalfGauge>(R.id.halfGauge1)
        val range = Range()
        range.color = Color.parseColor("#ce0000")
        range.from = 0.0
        range.to = 50.0

        val range2 = Range()
        range2.color = Color.parseColor("#E3E500")
        range2.from = 50.0
        range2.to = 100.0

        val range3 = Range()
        range3.color = Color.parseColor("#00b20b")
        range3.from = 100.0
        range3.to = 150.0

        //add color ranges to gauge
        halfGauge.addRange(range)
        halfGauge.addRange(range2)
        halfGauge.addRange(range3)

        //set min max and current value
        halfGauge.minValue = 0.0
        halfGauge.maxValue = 150.0
        halfGauge.value = 140.0
        halfGauge1.addRange(range)
        halfGauge1.addRange(range2)
        halfGauge1.addRange(range3)

        //set min max and current value
        halfGauge1.minValue = 0.0
        halfGauge1.maxValue = 150.0
        halfGauge1.value = 100.0
    }
    override fun onClick(position: Int) {

        val intent = Intent(applicationContext, Lot::class.java)
        val t: LotModel = LotModel(list[position].name,list[position].s1,list[position].s2,list[position].s3,list[position].s4,list[position].quality)
        intent.putExtra("t", t)
        intent.putExtra("map",map)
        intent.putExtra("nodelist",listNode)
        startActivity(intent)
         }

    companion object {
        private const val DATABASE_NAME = "PROJECT"
        const val url =
            "jdbc:mysql://nwarehouse.cmwq7a5jjeyz.ap-south-1.rds.amazonaws.com:3306/$DATABASE_NAME"
        const val username = "admin"
        const val password = "project123"
        const val TABLE_NAME = "TEST4"
    }
}
