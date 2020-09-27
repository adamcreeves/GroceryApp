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
import com.example.glossaryapp.R
import com.example.glossaryapp.fragments.PaymentFragment
import com.example.glossaryapp.models.Address
import kotlinx.android.synthetic.main.activity_orders.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*

class PaymentActivity : AppCompatActivity() {
    var textViewShoppingCartCount: TextView? = null
    var address: Address? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
//        address = intent.getSerializableExtra(Address.KEY_ADDRESS) as Address
//        Log.d("abc", address.toString())
        init()
    }

    private fun init() {
        setupToolbar()
        supportFragmentManager.beginTransaction().add(R.id.fragment_layout_payment, PaymentFragment()).commit()
//        button_payment_credit_card.setOnClickListener {
//            Toast.makeText(this, "Credit Cards Offline. Cash Payments Only", Toast.LENGTH_SHORT).show()
//        }
//        button_payment_cash.setOnClickListener {
//            var builder = AlertDialog.Builder(this)
//            builder.setTitle("Payment Confirmation")
//            builder.setMessage("Are you sure you want to place your order?")
//            builder.setPositiveButton("Yes", object: DialogInterface.OnClickListener{
//                override fun onClick(dialog: DialogInterface?, p1: Int) {
//                    startActivity(Intent(applicationContext, OrderPlacedActivity::class.java))
//                }
//            })
//            builder.setNegativeButton("No", object: DialogInterface.OnClickListener{
//                override fun onClick(dialog: DialogInterface?, p1: Int) {
//                    dialog?.dismiss()
//                }
//            })
//            var alertDialog = builder.create()
//            alertDialog.show()
//        }
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
