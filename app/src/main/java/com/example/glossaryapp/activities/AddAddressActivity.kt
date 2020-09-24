package com.example.glossaryapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.RadioGroup
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
    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        setupToolBar()
//        radioGroup = findViewById(R.id.radio_group_add_address)
//        val selectedRadioButton: Int = radioGroup!!.checkedRadioButtonId
//        radioButton = findViewById(selectedRadioButton)
//        Toast.makeText(baseContext, radioButton.text, Toast.LENGTH_SHORT).show()
        button_save_address.setOnClickListener {
            var pincode = edit_text_address_pincode.text.toString()
            var streetName = edit_text_street_name.text.toString()
            var city = edit_text_address_city.text.toString()
            var houseNo = edit_text_address_house_no.text.toString()
            var type = "Home"
            var userId = sessionManager.getUserId()
            Log.d("abc", userId)
            var params = HashMap<String, Any>()
            params["pincode"] = pincode.toInt()
            params["streetName"] = streetName
            params["city"] = city
            params["houseNo"] = houseNo
            params["type"] = type
            params["userId"] = userId

            var jsonObject = JSONObject(params as Map<*, *>)
            var request = JsonObjectRequest(
                Request.Method.POST, Endpoints.saveAddress(), jsonObject, {
                    Toast.makeText(
                        applicationContext,
                        "New address added successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    Log.d("abc", it.toString() + "Something else")
                },
                {
                    Log.d("abc", it.message.toString())
                }
            )
            Volley.newRequestQueue(this).add(request)
            finish()
        }
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
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }
        }
        return true
    }

}