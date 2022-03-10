package com.example.warehouse1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.warehouse1.dashboard.Dashboard

class SelectWarehouse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_warehouse)

     findViewById<Button>(R.id.bt_submit).setOnClickListener{
         startActivity(Intent(applicationContext, Dashboard::class.java))
     }
    }
}