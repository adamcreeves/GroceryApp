package com.example.glossaryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.glossaryapp.R
import com.example.glossaryapp.adapters.AdapterShoppingCart
import com.example.glossaryapp.database.DBHelper
import com.example.glossaryapp.models.Product
import kotlinx.android.synthetic.main.activity_shopping_cart.*

class ShoppingCartActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    var myList: ArrayList<Product> = ArrayList()
    private var adapterShoppingCart: AdapterShoppingCart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        dbHelper = DBHelper(this)
        init()
    }

    private fun init() {
        myList = dbHelper.getProducts()
        Toast.makeText(applicationContext, "Your cart items are loading...", Toast.LENGTH_SHORT).show()
        adapterShoppingCart = AdapterShoppingCart(this, myList)
        recycler_view.layoutManager = GridLayoutManager(this, 1)
        recycler_view.adapter = adapterShoppingCart
        adapterShoppingCart?.setData(myList)
    }

    override fun onResume() {
        super.onResume()
        myList = dbHelper.getProducts()
        adapterShoppingCart?.setData(myList)
    }
}