package com.example.myappproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu.*

class menu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        goto_bmi.setOnClickListener {
            val intent = Intent(this, SettingLanguage::class.java)
            startActivity(intent)
        }
        goto_calories.setOnClickListener {
            val intent = Intent(this, SettingLanguage::class.java)
            startActivity(intent)
        }
        goto_food.setOnClickListener {
            val intent = Intent(this,Food::class.java)
            startActivity(intent)
        }
    }
}
