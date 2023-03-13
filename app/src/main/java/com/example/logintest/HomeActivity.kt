package com.example.logintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.logintest.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set view binding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.buttonSignOut.setOnClickListener {
            // sign out user
            auth.signOut()

            //start another activity
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            //destroy current activity
            finish()
        }

        binding.buttonUpdatePassword.setOnClickListener {
            val user = auth.currentUser
            val password = binding.etPassword.text.toString()
            if (checkPasswordField()) {
                user?.updatePassword(password)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, it.exception?.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                        Log.e("error: ", it.exception.toString())
                    }
                }
            }
        }

        binding.buttonUpdateEmail.setOnClickListener {
            val user = auth.currentUser
            val email = binding.etEmail.text.toString()
            if (checkEmailField()) {
                user?.updateEmail(email)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Email updated successfully", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, it.exception?.message.toString(), Toast.LENGTH_SHORT)
                            .show()
                        Log.e("error: ", it.exception.toString())
                    }
                }
            }
        }
    }

    private fun checkPasswordField(): Boolean {
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

    private fun checkEmailField(): Boolean {
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

        return true
    }
}