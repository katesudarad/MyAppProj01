package com.example.myappproj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SimpleAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_food.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Food : AppCompatActivity(), View.OnClickListener {

    val COLLECTION = "FoodList"
    val F_ID = "id"
    val F_NANE = "name"
    val F_CALORIES = "calories"
    var doc_ID = ""

    lateinit var db : FirebaseFirestore
    lateinit var allFood : ArrayList<HashMap<String,Any>>
    lateinit var adapter: SimpleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        allFood = ArrayList()
        button_add.setOnClickListener(this)
        button_update.setOnClickListener(this)
        button_delete.setOnClickListener(this)
        IsData.setOnItemClickListener(itemClick)
    }


    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION).addSnapshotListener { querySnapshot, e->
            if(e!=null)Log.d("fireStore",e.message)
            showData()
            }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.button_add -> {

                val hm = HashMap<String,Any>()
                hm.set(F_ID,textid.text.toString())
                hm.set(F_NANE,dishName.text.toString())
                hm.set(F_CALORIES,NumberOfPartionSize.text.toString())
                db.collection(COLLECTION).document(textid.text.toString()).set(hm).addOnSuccessListener {
                    Toast.makeText(this,"Data sucessfully added",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { e ->
                    Toast.makeText(this,"Data unsucessfully added :${e.message}",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.button_update -> {
                val hm = HashMap<String,Any>()
                hm.set(F_ID,doc_ID)
                hm.set(F_NANE,dishName.text.toString())
                hm.set(F_CALORIES,NumberOfPartionSize.text.toString())
                db.collection(COLLECTION).document(doc_ID).update(hm).addOnSuccessListener {
                    Toast.makeText(this,"Data sucessfully update",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { e ->
                    Toast.makeText(this,"Data unsucessfully update :${e.message}",Toast.LENGTH_SHORT).show()
                }
            }
            R.id.button_delete -> {
                db.collection(COLLECTION).whereEqualTo(F_ID,doc_ID).get().addOnSuccessListener {result ->
                    for(doc in result){
                        db.collection(COLLECTION).document(doc_ID).delete().addOnSuccessListener {
                            Toast.makeText(this,"Data sucessfully deleteded",Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { e ->
                            Toast.makeText(this,"Data unsucessfully deleteded :${e.message}",Toast.LENGTH_SHORT).show()
                        }
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(this,"Cannot get data references :${e.message}",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val itemClick = AdapterView.OnItemClickListener{ parent, view, position, id ->
        val hm = allFood.get(position)
        doc_ID = hm.get(F_ID).toString()
        textid.setText(doc_ID)
        dishName.setText(hm.get(F_NANE).toString())
        NumberOfPartionSize.setText(hm.get(F_CALORIES).toString())
    }

    fun showData(){
        db.collection(COLLECTION).get().addOnSuccessListener{result ->
            allFood.clear()
            for (doc in result){
                val hm = HashMap<String,Any>()
                hm.set(F_ID,doc.get(F_ID).toString())
                hm.set(F_NANE,doc.get(F_NANE).toString())
                hm.set(F_CALORIES,doc.get(F_CALORIES).toString())
                allFood.add(hm)
            }
            adapter = SimpleAdapter(this,allFood,R.layout.row_data,
            arrayOf(F_ID,F_NANE,F_CALORIES),
                intArrayOf(R.id.textID,R.id.textname,R.id.textcalories))
            IsData.adapter = adapter
        }
    }
}
