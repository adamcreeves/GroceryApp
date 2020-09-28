package com.example.glossaryapp.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuItemCompat
import com.example.glossaryapp.R
import com.example.glossaryapp.database.DBHelper
import com.example.glossaryapp.helpers.SessionManager
import com.example.glossaryapp.models.Address
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*

class PaymentActivity : AppCompatActivity() {
    var textViewShoppingCartCount: TextView? = null
    var address: Address? = null
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        dbHelper = DBHelper(this)
        init()
    }

    private fun init() {
        setupToolbar()
        var sessionManager = SessionManager(this)

        var address = dbHelper.getAddress()
        text_view_payment_first_name.text = sessionManager.getFirstName()
        text_view_payment_house_no.text = address.houseNo
        text_view_payment_street_name.text = address.streetName
        text_view_payment_city.text = address.city
        text_view_payment_pincode.text = address.pincode.toString()


        var orderSummary = dbHelper.getOrderSummary()
        text_view_payment_subtotal.text = orderSummary[3]
        text_view_payment_discount.text = orderSummary[1]
        text_view_payment_total.text = orderSummary[2]
        text_view_payment_delivery.text = orderSummary[0]
        text_view_payment_amount.text = orderSummary[4]

        button_payment_credit_card.setOnClickListener{
            Toast.makeText(this, "Credit Card payment Unavailable", Toast.LENGTH_SHORT).show()
        }
        button_payment_change_address.setOnClickListener{
            startActivity(Intent(this, AddressActivity::class.java))
        }
        button_edit_cart.setOnClickListener{
            finish()
        }


        button_payment_place_order.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Payment Confirmation")
            builder.setMessage("Are you sure you want to place your order?")
            builder.setPositiveButton("Yes", object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, p1: Int) {



                    startActivity(Intent(applicationContext, OrderPlacedActivity::class.java))
                }
            })
            builder.setNegativeButton("No", object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, p1: Int) {
                    dialog?.dismiss()
                }
            })
            var alertDialog = builder.create()
            alertDialog.show()
        }
    }

    private fun setupToolbar() {
        var toolbar = toolbar
        toolbar.title = "Your Cart"
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
}
