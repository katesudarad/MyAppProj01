package com.example.myappproj

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.vicky.multilanguageexample.MyPreference
import com.example.vicky.multilanguageexample.com.example.myappproj.MyContextWrapper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register_page.*

class MainActivity : AppCompatActivity() {
    lateinit var myPreference: MyPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        welcome_text.setOnClickListener {
            startActivity(Intent(this,SettingLanguage::class.java))
        }

        Main_login_button.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }
        Main_register_button.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
        Goback.setOnClickListener {
            val intent = Intent(this, SettingLanguage::class.java)
            startActivity(intent)
        }

    }
    override fun attachBaseContext(newBase: Context?) {
        myPreference = MyPreference(newBase!!)
        val lang = myPreference.getLoginCount()
        super.attachBaseContext(lang?.let { MyContextWrapper.wrap(newBase,lang) })
    }




}




