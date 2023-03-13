package com.example.logintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.logintest.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set view binding
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //use to remove action bar
        supportActionBar?.hide()

        auth = Firebase.auth

        binding.buttonSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            // if fields are not empty
            if (checkField()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // if successfully logged in
                        Toast.makeText(this, "Successfully logged in", Toast.LENGTH_SHORT).show()

                        //go to another activity
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        //destroy current activity
                        finish()
                    } else {
                        //error
                        Toast.makeText(this, it.exception?.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                        Log.e("error: ", it.exception.toString())
                    }
                }
            }
        }

        binding.tvCreateAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            //destroy current activity
            finish()
        }

        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            //destroy current activity
            finish()
        }
    }

    private fun checkField(): Boolean {
        val email = binding.etEmail.text.toString()
        if (binding.etEmail.text.toString() == ""
        ) {
            binding.textInputLayoutEmail.error = "This is required field"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textInputLayoutEmail.error = "Check email format"
            return false
        }
        if (binding.etPassword.text.toString() == "") {
            binding.textInputLayoutPassword.error = "This is required field"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }
        if (binding.etPassword.length() < 6) {
            binding.textInputLayoutPassword.error = "Password should be at least 6 characters long"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }

        return true
    }
}