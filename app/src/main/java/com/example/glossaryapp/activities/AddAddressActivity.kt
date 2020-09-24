package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.app.Configure
import com.example.glossaryapp.app.Endpoints
import com.example.glossaryapp.helpers.SessionManager
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        sessionManager = SessionManager(this)
        init()
    }

    private fun setupToolBar() {
        var toolbar = toolbar
        toolbar.title = "Add New Address"
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
                startActivity(Intent(applicationContext, StartActivity::class.java))
            }
        }
        return true
    }

    private fun init() {
        setupToolBar()
        button_save_address.setOnClickListener {
            var pincode = edit_text_address_pincode.text.toString()
            var streetName = edit_text_street_name.text.toString()
            var city = edit_text_address_city.text.toString()
            var houseNo = edit_text_address_house_no.text.toString()
            var type = edit_text_address_type.text.toString()
            var userId = sessionManager.getUserId()
            var params = HashMap<String, Any>()
            params["pincode"] = pincode.toInt()
            params["streetName"] = streetName
            params["city"] = city
            params["houseNo"] = houseNo
            params["type"] = type
            params["userId"] = userId

            var jsonObject = JSONObject(params as Map<*, *>)
            var request = JsonObjectRequest(
                Request.Method.POST, Endpoints.getAddress(), jsonObject, {
                    Toast.makeText(
                        applicationContext,
                        "Registration successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this, AddressActivity::class.java))
                    Log.d("abc", it.toString())
                },
                {
                    Log.d("abc", it.message.toString())
                }
            )
            Volley.newRequestQueue(this).add(request)
            Toast.makeText(
                applicationContext,
                "New employee record successfully added!",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(applicationContext, AddressActivity::class.java))
        }
    }
}