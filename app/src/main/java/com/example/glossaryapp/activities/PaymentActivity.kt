package com.example.glossaryapp.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.glossaryapp.R
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.app_bar.*

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        init()
    }

    private fun init() {
        setupToolbar()
        button_payment_credit_card.setOnClickListener {
            Toast.makeText(this, "Credit Cards Offline. Cash Payments Only", Toast.LENGTH_SHORT).show()
        }
        button_payment_cash.setOnClickListener {
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
        toolbar.title = "Select Payment Method"
        setSupportActionBar(toolbar)
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
        }
        return true
    }

}