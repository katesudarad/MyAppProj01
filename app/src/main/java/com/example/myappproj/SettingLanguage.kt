package com.example.myappproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.vicky.multilanguageexample.MyPreference
import kotlinx.android.synthetic.main.activity_setting_language.*

class SettingLanguage : AppCompatActivity() {

    lateinit var myPreference: MyPreference
    var languageList:Array<String> = arrayOf("EN","TH")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_language)

        myPreference = MyPreference(this)
        spinner.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,languageList)

        val lang = myPreference.getLoginCount()
        val index = languageList.indexOf(lang)
        if(index >= 0){
            spinner.setSelection(index)
        }

        button.setOnClickListener {
            myPreference.setLoginCount(languageList[spinner.selectedItemPosition])
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}