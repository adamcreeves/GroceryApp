package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.glossaryapp.R
import com.example.glossaryapp.adapters.AdapterShoppingCart
import com.example.glossaryapp.database.DBHelper
import com.example.glossaryapp.helpers.SessionManager
import com.example.glossaryapp.models.CartProductData
import com.example.glossaryapp.models.Product
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*

class ShoppingCartActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper
    var myList: ArrayList<CartProductData> = ArrayList()
    lateinit var sessionManager: SessionManager
    private var adapterShoppingCart: AdapterShoppingCart? = null
    var textViewShoppingCartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        sessionManager = SessionManager(this)
        dbHelper = DBHelper(this)
        init()
    }

    private fun init() {
        setupToolbar()
        myList = dbHelper.getProducts()
        adapterShoppingCart = AdapterShoppingCart(this, myList)
        recycler_view.layoutManager = GridLayoutManager(this, 1)
        recycler_view.adapter = adapterShoppingCart
        adapterShoppingCart?.setData(myList)
        runningTotals()
        button_cart_to_checkout.setOnClickListener {
            if (sessionManager.getQuickLogin()) {
                startActivity(Intent(this, PaymentActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    private fun setupToolbar() {
        var toolbar = toolbar
        toolbar.title = "Your Shopping Cart"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)
        var item = menu.findItem(R.id.action_cart)
        MenuItemCompat.setActionView(item, R.layout.layout_menu_cart)
        var view = MenuItemCompat.getActionView(item)
        textViewShoppingCartCount = view.text_view_cart_count
        view.setOnClickListener {
            Toast.makeText(applicationContext, "You're viewing your cart", Toast.LENGTH_SHORT).show()
        }
        updateShoppingCartCount()
        return true
    }

    private fun updateShoppingCartCount() {
        var myCount = 1
        if(myCount == 0) {
            textViewShoppingCartCount?.visibility = View.INVISIBLE
        } else {
            textViewShoppingCartCount?.visibility = View.VISIBLE
            textViewShoppingCartCount?.text = myCount.toString()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }


    override fun onResume() {
        super.onResume()
        myList = dbHelper.getProducts()
        adapterShoppingCart?.setData(myList)
    }

    fun runningTotals() {
        if (myList.size < 1) {
            view_cart_if_items.visibility = View.INVISIBLE
        } else {
            text_view_cart_headline.text = "Products to Purchase"
            var subtotal = 0.0
            var total = 0.0
            for (i in 0 until myList.size) {
                total += myList[i].quantity * myList[i].price
                subtotal += myList[i].quantity * myList[i].mrp
            }
            text_view_cart_subtotal.text = "$subtotal"
            text_view_cart_discount.text = "${total - subtotal}"
            text_view_cart_total.text = "$total"
        }
    }
}