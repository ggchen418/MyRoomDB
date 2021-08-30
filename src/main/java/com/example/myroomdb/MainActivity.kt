package com.example.myroomdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myroomdb.data.Product
import com.example.myroomdb.data.ProductAdapter
import com.example.myroomdb.data.ProductDB
import com.example.myroomdb.data.ProductDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var dao: ProductDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dao = ProductDB.getInstance(application).productDao

        val recyclerView : RecyclerView = findViewById(R.id.myRecycleView)


        val btnInsert :Button = findViewById(R.id.btnInsert)
        btnInsert.setOnClickListener(){

            val name :String  = findViewById<TextView>(R.id.tfName).text.toString()
            val price:Double =  findViewById<TextView>(R.id.tfPrice).text.toString().toDouble()

            val p = Product(0, name, price)

            CoroutineScope(IO).launch {
                dao.insertProduct(p)
            }

        }

        val btnGet :Button = findViewById(R.id.btnGet)
        btnGet.setOnClickListener(){

            CoroutineScope(IO).launch {

                var strName = ""
                val pList : List<Product> = dao.getAll()

                for(p : Product in pList){
                    strName += p.name + "" + p.price + "\n"
                }


                CoroutineScope(Main).launch{
                    recyclerView.adapter = ProductAdapter(pList)
                    recyclerView.setHasFixedSize(true)
                }

            }

        }



    }
}