package com.example.warehouse1.aws

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import com.example.warehouse1.R
import android.text.method.ScrollingMovementMethod
import kotlin.Throws
import com.example.warehouse1.aws.ShowActivity
import com.example.warehouse1.viewdetails.LotModel
import com.loopj.android.http.AsyncHttpClient.log
import jxl.Sheet
import jxl.Workbook
import java.io.InputStream
import java.lang.Exception
import java.lang.StringBuilder
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class ShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        findViewById<TextView>(R.id.tv_rds).movementMethod = ScrollingMovementMethod()
        try {
            utilFun()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    @Throws(SQLException::class)
    private fun utilFun() {
        Thread {
            val records = StringBuilder()
            try {
                Class.forName("com.mysql.jdbc.Driver")
                val connection = DriverManager.getConnection(url, username, password)
                val statement = connection.createStatement()
                val rs:ResultSet = statement.executeQuery("Select * FROM $TABLE_NAME ORDER BY CURRENTDATE DESC,CURRENTTIME DESC LIMIT 100")
                while (rs.next()){
                    if(map[rs.getString(1)]==null)
                    map[rs.getString(1)]= mutableListOf(mutableListOf<String>(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)," "+rs.getString(8)))
                    else map[rs.getString(1)]?.add((mutableListOf<String>(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)," "+rs.getString(8))))

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
                    log.d("NODEE","\n$key----->"+map[key].toString())
                }
                log.d("MAP SIZE",map.size.toString())
                connection.close()
                update(map)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            runOnUiThread { findViewById<TextView>(R.id.tv_rds).text = records.toString()
            }
        }.start()

    }

    private fun update(map: MutableMap<String, MutableList<MutableList<String>>>) {

    }

    companion object {
        private const val DATABASE_NAME = "PROJECT"
        const val url =
            "jdbc:mysql://nwarehouse.cmwq7a5jjeyz.ap-south-1.rds.amazonaws.com:3306/$DATABASE_NAME"
        const val username = "admin"
        const val password = "project123"
        const val TABLE_NAME = "TEST4"
        val map: MutableMap<String,MutableList<MutableList<String>>> = mutableMapOf<String,MutableList<MutableList<String>>>()

    }
}