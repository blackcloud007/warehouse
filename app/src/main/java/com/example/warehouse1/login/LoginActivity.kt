package com.example.warehouse1.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.warehouse1.R
import com.example.warehouse1.dashboard.Dashboard
import com.example.warehouse1.viewdetails.Node.Lot

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        findViewById<Button>(R.id.login).setOnClickListener{
            if (findViewById<EditText>(R.id.username).text.trim().toString()=="1" && findViewById<EditText>(R.id.password).text.trim().toString()=="1")
            {
                Toast.makeText(this,"Logging in!!",Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, Dashboard::class.java)
                startActivity(intent)
            }else{
                    Toast.makeText(this,"Wrong credentials !! , Try again ",Toast.LENGTH_SHORT).show()
            }
        }
    }
}