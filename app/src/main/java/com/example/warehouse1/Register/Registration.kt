package com.example.warehouse1.Register

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
import com.example.warehouse1.login.LoginActivity
import com.google.android.material.textfield.TextInputLayout


class Registration : AppCompatActivity() {
    var name: EditText? = null
    var email: EditText? = null
    var phone: EditText? = null
    var password: EditText? = null
    var register: Button? = null
    var login: TextView? = null
    var isNameValid = false
    var isEmailValid = false
    var isPhoneValid = false
    var isPasswordValid = false
    var nameError: TextInputLayout? = null
    var emailError: TextInputLayout? = null
    var phoneError: TextInputLayout? = null
    var passError: TextInputLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        name = findViewById<View>(R.id.name) as EditText
        email = findViewById<View>(R.id.email) as EditText
        phone = findViewById<View>(R.id.phone) as EditText
        password = findViewById<View>(R.id.password) as EditText
        login = findViewById<View>(R.id.login) as TextView
        register = findViewById<View>(R.id.register) as Button
        nameError = findViewById<View>(R.id.nameError) as TextInputLayout
        emailError = findViewById<View>(R.id.emailError) as TextInputLayout
        phoneError = findViewById<View>(R.id.phoneError) as TextInputLayout
        passError = findViewById<View>(R.id.passError) as TextInputLayout
        register!!.setOnClickListener { SetValidation() }
        login!!.setOnClickListener { // redirect to LoginActivity
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun SetValidation() {
        // Check for a valid name.
       if (name!!.text.toString().isEmpty()) {
            nameError!!.error = resources.getString(R.string.name_error)
            isNameValid = false
        } else {
            isNameValid = true
            nameError!!.isErrorEnabled = false
        }

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

        // Check for a valid phone number.
       if (phone!!.text.toString().isEmpty()) {
            phoneError!!.error = resources.getString(R.string.phone_error)
            isPhoneValid = false
        } else {
            isPhoneValid = true
            phoneError!!.isErrorEnabled = false
        }

        //Check for a valid password.
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
       // if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid) {
            Toast.makeText(applicationContext, "Successfully Regstered", Toast.LENGTH_SHORT).show()
            finish()
       // }
    }
}