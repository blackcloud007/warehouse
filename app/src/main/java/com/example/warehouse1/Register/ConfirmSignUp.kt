package com.example.warehouse1.Register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.amplifyframework.core.Amplify
import com.example.warehouse1.R

class ConfirmSignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_sign_up)

        val username= intent.getStringExtra("username")
        findViewById<Button>(R.id.bt_confirm_account_submit).setOnClickListener {
            if (username != null) {
                Amplify.Auth.confirmSignUp(
                    username, findViewById<EditText>(R.id.confirm_account_code).text.toString().trim(),
                    { result ->
                        if (result.isSignUpComplete) {
                            Log.i("AuthQuickstart", "Confirm signUp succeeded")
                            runOnUiThread{ Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()}
                            finish()
                        } else {
                            Log.i("AuthQuickstart","Confirm sign up not complete")
                        }
                    },
                    { Log.e("AuthQuickstart", "Failed to confirm sign up", it) }
                )
            }
        }

    }
}