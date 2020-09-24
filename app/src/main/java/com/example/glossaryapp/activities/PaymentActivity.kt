package com.example.glossaryapp.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.glossaryapp.R
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        init()
    }

    private fun init() {
        button_payment_credit_card.setOnClickListener {
            Toast.makeText(this, "Credit Cards Offline. Cash Payments Only", Toast.LENGTH_SHORT).show()
        }
        button_payment_cash.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Payment Confirmation")
            builder.setMessage("Are you sure you want to place your order?")
            builder.setPositiveButton("Yes", object: DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, p1: Int) {
                    Toast.makeText(applicationContext, "Order placed successfully!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
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
}