package com.example.myappproj
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns

import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login_page.*
import kotlinx.android.synthetic.main.activity_register_page.*

class LoginPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login_page)
        auth = FirebaseAuth.getInstance()

        login_button_login.setOnClickListener {

            val email = email_edittext_login.text.toString()
            val password = password_edittext_login.text.toString()

            Log.d("Login", "Attempt login with email/pw: $email/***")

             FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                 .addOnCompleteListener {
                     if (!it.isSuccessful) return@addOnCompleteListener
                     // else if successful
                     Log.d("Main", "Successfully created user with uid: ${it.result?.user?.uid}")
                     val currentUser = auth.currentUser

                     if (currentUser != null) {
                         if(currentUser.isEmailVerified) {
                             startActivity(Intent(this, menu::class.java))
                             finish()
                         }else{
                             Toast.makeText(
                                 baseContext, "Please verify your email address.",
                                 Toast.LENGTH_SHORT
                             ).show()
                         }
                     } else {
                         Toast.makeText(
                             baseContext, "Login failed.",
                             Toast.LENGTH_SHORT
                         ).show()
                     }
                 }
        }
    }

}