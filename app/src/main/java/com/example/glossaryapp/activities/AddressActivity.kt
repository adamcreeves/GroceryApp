package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.adapters.AdapterAddress
import com.example.glossaryapp.app.Endpoints
import com.example.glossaryapp.database.DBHelper
import com.example.glossaryapp.helpers.SessionManager
import com.example.glossaryapp.models.Address
import com.example.glossaryapp.models.AddressResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*

class AddressActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    var myList: ArrayList<Address> = ArrayList()
    var adapterAddress: AdapterAddress? = null
    var textViewShoppingCartCount: TextView? = null
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        dbHelper = DBHelper(this)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        Toast.makeText(this, "Your saved addresses are loading...", Toast.LENGTH_SHORT).show()
        setupToolBar()
        getData()
        adapterAddress = AdapterAddress(this, myList)
        recycler_view_addresses.layoutManager = LinearLayoutManager(this)
        recycler_view_addresses.adapter = adapterAddress
        button_add_new_address.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java))
        }
    }

    private fun getData() {
        var userId = sessionManager.getUserId()
        var request = StringRequest(Request.Method.GET, Endpoints.getOrDeleteAddress(userId), {
            var gson = Gson()
            var addressResult = gson.fromJson(it, AddressResult::class.java)
            myList.addAll(addressResult.data)
            adapterAddress?.setData(myList)
        },
            {

            }
        )
        Volley.newRequestQueue(this).add(request)
    }

    private fun setupToolBar() {
        var toolbar = toolbar
        toolbar.title = "Select Shipping Address"
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
            startActivity(Intent(applicationContext, ShoppingCartActivity::class.java))
        }
        updateShoppingCartCount()
        return true
    }

    private fun updateShoppingCartCount() {
        var myCount = dbHelper.getCartTotalCount()
        if (myCount == 0) {
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

}