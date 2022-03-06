package com.example.warehouse1.temperature

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warehouse1.R
import com.loopj.android.http.AsyncHttpClient
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class Temperature : AppCompatActivity() {
    lateinit var context: Context
    private lateinit var rv: RecyclerView
    lateinit var map: HashMap<String,MutableList<MutableList<String>>>
    val listNode = mutableListOf<TemperatureModel>()
    var adapter: TemperatureAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature)
        rv=findViewById(R.id.rv_temp)
        context=this
        map= hashMapOf<String,MutableList<MutableList<String>>>()
        getDataFromServer().execute()
    }

    internal inner class getDataFromServer(): AsyncTask<Void, Void, HashMap<String, MutableList<MutableList<String>>>>() {
        lateinit var progressDialog: ProgressDialog
        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog= ProgressDialog(context)
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
                adapter= TemperatureAdapter(context,listNode,this@Temperature)
                rv.layoutManager= LinearLayoutManager(context)
                rv.adapter=adapter
            }
            else
                Toast.makeText(context,"Error in Loading", Toast.LENGTH_SHORT).show()
        }

    }

    private fun order(result: HashMap<String, MutableList<MutableList<String>>>) {
        for(i in result.keys)
        {
            for (j in result[i]!!){
                listNode.add(TemperatureModel(i,j[0],j[1],j[2],j[3]))
            }
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
            val rs: ResultSet = statement.executeQuery("Select * FROM ${TABLE_NAME} ORDER BY CURRENTDATE DESC,CURRENTTIME DESC LIMIT 100")
            while (rs.next()){
                if(map[rs.getString(1)]==null)
                    map[rs.getString(1)]= mutableListOf(mutableListOf<String>(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)))
                else map[rs.getString(1)]?.add((mutableListOf<String>(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5))))

                //records.append("NODEID: ").append(rs.getString(1))
                //    .append(",Temp ").append(rs.getString(2))
                //    .append(",hum").append(rs.getString(3))
                //     .append(",CURRENTDATE ").append(rs.getString(4))
                //     .append(",CURRENTTIME ").append(rs.getString(5))
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
    companion object {
        private const val DATABASE_NAME = "PROJECT"
        const val url =
            "jdbc:mysql://nwarehouse.cmwq7a5jjeyz.ap-south-1.rds.amazonaws.com:3306/$DATABASE_NAME"
        const val username = "admin"
        const val password = "project123"
        const val TABLE_NAME = "TEST5"
    }
}