package com.example.warehouse1.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify
import com.example.warehouse1.R
import com.example.warehouse1.Register.Registration
import com.example.warehouse1.SelectWarehouse
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {
    var email: EditText? = null
    var username:EditText?=null
    var password: EditText? = null
    var login: Button? = null
    var register: TextView? = null
    var isEmailValid = false
    var isPasswordValid = false
    var isUsernameValid=false
    var emailError: TextInputLayout? = null
    var usernameError:TextInputLayout?=null
    var passError: TextInputLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       // email = findViewById<View>(R.id.email) as EditText
        username=findViewById<View>(R.id.username)as EditText
        password = findViewById<View>(R.id.password) as EditText
        login = findViewById<View>(R.id.login) as Button
        register = findViewById<View>(R.id.register) as TextView
       // emailError = findViewById<View>(R.id.emailError) as TextInputLayout
        passError = findViewById<View>(R.id.passError) as TextInputLayout
        usernameError =findViewById<View>(R.id.usernameError) as TextInputLayout
        login!!.setOnClickListener { SetValidation() }
        register!!.setOnClickListener { // redirect to RegisterActivity
            val intent = Intent(applicationContext, Registration::class.java)
            startActivity(intent)
        }
    }

    private fun SetValidation() {
        // Check for a valid email address.
    /*    if (email!!.text.toString().isEmpty()) {
            emailError!!.error = resources.getString(R.string.email_error)
            isEmailValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email!!.text.toString()).matches()) {
            emailError!!.error = resources.getString(R.string.error_invalid_email)
            isEmailValid = false
        } else {
            isEmailValid = true
            emailError!!.isErrorEnabled = false
        }
*/
        // Check for a valid password.
        if (password!!.text.toString().trim().isEmpty()) {
            passError!!.error = resources.getString(R.string.password_error)
            isPasswordValid = false
        } else if (password!!.text.length < 6) {
            passError!!.error = resources.getString(R.string.error_invalid_password)
            isPasswordValid = false
        } else {
            isPasswordValid = true
            passError!!.isErrorEnabled = false
        }
        // Check for valid name
        if(username!!.text.toString().trim().isEmpty()){
            usernameError!!.error="Please enter a username"
            isUsernameValid=false
        }
        else
        {
            isUsernameValid=true
        }
        if (isPasswordValid&&isUsernameValid) {
            Amplify.Auth.signIn(
                username!!.text.toString().trim(), password!!.text.toString().trim(),
                { result ->
                    if (result.isSignInComplete) {
                        Log.i("AuthQuickstart", "Sign in succeeded")
                        runOnUiThread {Toast.makeText(applicationContext, "Successfully", Toast.LENGTH_SHORT).show()}
                        val intent = Intent(applicationContext, SelectWarehouse::class.java)
                        startActivity(intent)
                    } else {
                        runOnUiThread{Toast.makeText(applicationContext, "Invalid credentials.Try again!!", Toast.LENGTH_SHORT).show()}
                        Log.i("AuthQuickstart", "Sign in not complete")
                    }
                },
                { Log.e("AuthQuickstart", "Failed to sign in", it) }
            )

        }
    }
}