package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.app.Endpoints
import com.example.glossaryapp.helpers.SessionManager
import com.example.glossaryapp.models.Address
import com.example.glossaryapp.models.AddressResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton
    var textViewShoppingCartCount: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        setupToolBar()
        button_save_address.setOnClickListener {
            radioGroup = findViewById(R.id.radio_group_add_address)
            val selectedRadioButton: Int = radioGroup!!.checkedRadioButtonId
            radioButton = findViewById(selectedRadioButton)
            var pincode = edit_text_address_pincode.text.toString()
            var streetName = edit_text_street_name.text.toString()
            var city = edit_text_address_city.text.toString()
            var houseNo = edit_text_address_house_no.text.toString()
            var type = radioButton.text.toString()
            var userId = sessionManager.getUserId()
            if (pincode == "" || streetName == "" || city == "" || houseNo == "") {
                Toast.makeText(
                    baseContext,
                    "You haven't completed filling out your address",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

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
                        val gson = Gson()
                        var addressResponse = gson.fromJson(it.toString(), AddressResult::class.java)
                        sessionManager.saveAddress(addressResponse.data[0])
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
    }

    private fun setupToolBar() {
        var toolbar = toolbar
        toolbar.title = "Add New Address"
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
        var myCount = 1
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