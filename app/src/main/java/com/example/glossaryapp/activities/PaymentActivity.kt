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
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.app.Endpoints
import com.example.glossaryapp.database.DBHelper
import com.example.glossaryapp.helpers.SessionManager
import com.example.glossaryapp.models.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*
import org.json.JSONObject

class PaymentActivity : AppCompatActivity() {
    var textViewShoppingCartCount: TextView? = null
    var addres: Address? = null
    var cartProductData: ArrayList<CartProductData>? = null
    lateinit var dbHelper: DBHelper
    lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        dbHelper = DBHelper(this)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        setupToolbar()

        addres = dbHelper.getAddress()
        var order = dbHelper.getOrderSummary()
        text_view_payment_first_name.text = sessionManager.getFirstName()
        text_view_payment_house_no.text = addres!!.houseNo
        text_view_payment_street_name.text = addres!!.streetName
        text_view_payment_city.text = addres!!.city
        text_view_payment_pincode.text = addres!!.pincode.toString()


        text_view_payment_subtotal.text = order[3]
        text_view_payment_discount.text = order[1]
        text_view_payment_total.text = order[4]
        text_view_payment_delivery.text = order[0]
        text_view_payment_amount.text = order[2]

        button_payment_credit_card.setOnClickListener {
            Toast.makeText(this, "Credit Card payment Unavailable", Toast.LENGTH_SHORT).show()
        }
        button_payment_change_address.setOnClickListener {
            startActivity(Intent(this, AddressActivity::class.java))
        }
        button_edit_cart.setOnClickListener {
            finish()
        }

        button_payment_place_order.setOnClickListener {

//
            startActivity(
                Intent(
                    applicationContext,
                    OrderPlacedActivity::class.java
                )
            )


//            var builder = AlertDialog.Builder(this)
//            builder.setTitle("Payment Confirmation")
//            builder.setMessage("Are you sure you want to place your order?")
//            builder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
//                override fun onClick(dialog: DialogInterface?, p1: Int) {
//                    var gson = Gson()
//                    var obj = gson.toJson(createOrderToPost())
//                    var jsonObject = JSONObject(obj)
//                    val request =
//                        JsonObjectRequest(Request.Method.POST, Endpoints.postOrders(), jsonObject, {
//                            dbHelper.clearCart()
//                        },
//                            {
//
//                            }
//                        )
//                    Volley.newRequestQueue(applicationContext).add(request)
//                    startActivity(
//                        Intent(
//                            applicationContext,
//                            OrderPlacedActivity::class.java
//                        )
//                    )
//                }
//            })
//            builder.setNegativeButton("No", object : DialogInterface.OnClickListener {
//                override fun onClick(dialog: DialogInterface?, p1: Int) {
//                    dialog?.dismiss()
//                }
//            })
//            var alertDialog = builder.create()
//            alertDialog.show()
        }
    }

    fun createOrderToPost(): PaymentResponse {
        var userId = sessionManager.getUserId()
        var order = dbHelper.getOrderSummary()
        var orderStatus = "Confirmed"
        var orderSummary = OrderSummary(
            order[0].toInt(),
            order[1].toInt(),
            order[2].toInt(),
            order[3].toInt(),
            order[4].toInt()
        )
        var user = PaymentUser(
            email = sessionManager.getEmail(),
            mobile = sessionManager.getMobile(),
            name = sessionManager.getFirstName()
        )
        addres = dbHelper.getAddress()
        var shippingAddress =
            ShippingAddress(addres!!.city, addres!!.houseNo, addres!!.pincode, addres!!.streetName)
        var payment = Payment("cash", "completed")
        var products = dbHelper.getOrderProducts()
        return PaymentResponse(
            userId,
            orderStatus,
            orderSummary,
            user,
            shippingAddress,
            payment,
            products
        )
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
