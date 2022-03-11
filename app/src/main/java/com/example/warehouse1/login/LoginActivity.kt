package com.example.warehouse1.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.warehouse1.R
import com.example.warehouse1.Register.Registration
import com.example.warehouse1.SelectWarehouse
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {
    var email: EditText? = null
    var password: EditText? = null
    var login: Button? = null
    var register: TextView? = null
    var isEmailValid = false
    var isPasswordValid = false
    var emailError: TextInputLayout? = null
    var passError: TextInputLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById<View>(R.id.email) as EditText
        password = findViewById<View>(R.id.password) as EditText
        login = findViewById<View>(R.id.login) as Button
        register = findViewById<View>(R.id.register) as TextView
        emailError = findViewById<View>(R.id.emailError) as TextInputLayout
        passError = findViewById<View>(R.id.passError) as TextInputLayout
        login!!.setOnClickListener { SetValidation() }
        register!!.setOnClickListener { // redirect to RegisterActivity
            val intent = Intent(applicationContext, Registration::class.java)
            startActivity(intent)
        }
    }

    private fun SetValidation() {
        // Check for a valid email address.
        if (email!!.text.toString().isEmpty()) {
            emailError!!.error = resources.getString(R.string.email_error)
            isEmailValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email!!.text.toString()).matches()) {
            emailError!!.error = resources.getString(R.string.error_invalid_email)
            isEmailValid = false
        } else {
            isEmailValid = true
            emailError!!.isErrorEnabled = false
        }

        // Check for a valid password.
        if (password!!.text.toString().isEmpty()) {
            passError!!.error = resources.getString(R.string.password_error)
            isPasswordValid = false
        } else if (password!!.text.length < 6) {
            passError!!.error = resources.getString(R.string.error_invalid_password)
            isPasswordValid = false
        } else {
            isPasswordValid = true
            passError!!.isErrorEnabled = false
        }
        if (isEmailValid && isPasswordValid) {

            Toast.makeText(applicationContext, "Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(applicationContext, SelectWarehouse::class.java)
            startActivity(intent)
        }
    }
}