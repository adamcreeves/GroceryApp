package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import com.example.glossaryapp.R
import com.example.glossaryapp.app.Configure
import com.example.glossaryapp.database.DBHelper
import com.example.glossaryapp.helpers.SessionManager
import com.example.glossaryapp.models.CartProductData
import com.example.glossaryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.layout_menu_cart.view.*

class ProductDetailActivity : AppCompatActivity() {
    var product: Product? = null
    lateinit var dbHelper: DBHelper
    lateinit var sessionManager: SessionManager
    var textViewShoppingCartCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        sessionManager = SessionManager(this)
        product = intent.getSerializableExtra(Product.KEY_PRODUCT) as Product
        dbHelper = DBHelper(this)
        init()
    }

    private fun init() {
        setupToolBar()
        var quantity = 0
        text_view_details_product_name.text = product?.productName
        text_view_details_description.text = product?.description
        text_view_details_price.text = product?.price.toString()
        Picasso.get()
            .load(Configure.IMAGE_URL + product?.image)
            .resize(200, 200)
            .centerCrop()
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_didnt_load)
            .into(image_view_details_image)
        button_add_to_cart.setOnClickListener {
            if (product!!.quantity > 0) {
                var id = product!!._id
                quantity += 1
                var mrp = product!!.mrp
                var productName = product!!.productName
                var price = product!!.price
                var image = Configure.IMAGE_URL + product!!.image
                dbHelper.addProduct(CartProductData(id, quantity, mrp, productName, price, image))
                Toast.makeText(
                    applicationContext,
                    "Item Added",
                    Toast.LENGTH_SHORT
                ).show()
            } else Toast.makeText(
                applicationContext,
                "This item is sold out",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupToolBar() {
        var toolbar = toolbar
        toolbar.title = "Selected Item"
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