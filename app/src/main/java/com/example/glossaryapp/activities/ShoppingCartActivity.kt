package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.glossaryapp.R
import com.example.glossaryapp.adapters.AdapterShoppingCart
import com.example.glossaryapp.database.DBHelper
import com.example.glossaryapp.models.CartProductData
import com.example.glossaryapp.models.Product
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import kotlinx.android.synthetic.main.app_bar.*

class ShoppingCartActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    var myList: ArrayList<CartProductData> = ArrayList()
    private var adapterShoppingCart: AdapterShoppingCart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        dbHelper = DBHelper(this)
        init()
    }

    private fun setupToolbar() {
        var toolbar = toolbar
        toolbar.title = "Your Cart"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_cart -> Toast.makeText(
                applicationContext,
                "You're already in your cart, my dude",
                Toast.LENGTH_SHORT
            ).show()
            R.id.action_home -> startActivity(
                Intent(
                    applicationContext,
                    HomeActivity::class.java
                )
            )
            R.id.action_settings -> Toast.makeText(
                applicationContext,
                "You just clicked on Settings. Great work!",
                Toast.LENGTH_SHORT
            ).show()
            R.id.action_profile -> Toast.makeText(
                applicationContext,
                "You just clicked on Profile. Great work!",
                Toast.LENGTH_SHORT
            ).show()
        }
        return true
    }

    private fun init() {
        setupToolbar()
        myList = dbHelper.getProducts()
        Toast.makeText(applicationContext, "Your cart items are loading...", Toast.LENGTH_SHORT)
            .show()

        adapterShoppingCart = AdapterShoppingCart(this, myList)
        recycler_view.layoutManager = GridLayoutManager(this, 1)
        recycler_view.adapter = adapterShoppingCart
        adapterShoppingCart?.setData(myList)
        button_cart_to_checkout.setOnClickListener {
            startActivity(Intent(applicationContext, AddressActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        myList = dbHelper.getProducts()
        adapterShoppingCart?.setData(myList)
    }
}