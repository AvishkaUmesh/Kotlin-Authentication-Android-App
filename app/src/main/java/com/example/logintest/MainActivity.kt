package com.example.logintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //this use to hide action bar
        supportActionBar?.hide()

        auth = Firebase.auth

        //use to put delay
        Handler(Looper.getMainLooper()).postDelayed({
            val user = auth.currentUser
            if (user != null) {
                //use to start home activity
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                //use to start main activity
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
            //use to destroy current activity
            finish()
        }, 2000)
    }
}