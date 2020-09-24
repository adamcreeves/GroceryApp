package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.adapters.AdapterAddress
import com.example.glossaryapp.app.Endpoints
import com.example.glossaryapp.helpers.SessionManager
import com.example.glossaryapp.models.Address
import com.example.glossaryapp.models.AddressResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.app_bar.*

class AddressActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    var myList: ArrayList<Address> = ArrayList()
    var adapterAddress: AdapterAddress? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
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
        button_to_payment.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
    }

    private fun getData() {
        var userId = sessionManager.getUserId()
        var request = StringRequest(Request.Method.GET, Endpoints.getAddress(userId), {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_cart -> startActivity(
                Intent(
                    applicationContext,
                    ShoppingCartActivity::class.java
                )
            )
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
            R.id.action_logout -> {
                Toast.makeText(applicationContext, "You just logged out :(", Toast.LENGTH_SHORT)
                    .show()
                sessionManager.logout()
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }
        }
        return true
    }

}