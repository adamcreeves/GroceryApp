package com.example.glossaryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.glossaryapp.R
import com.example.glossaryapp.app.Configure
import com.example.glossaryapp.app.Endpoints
import com.example.glossaryapp.models.Product
import com.example.glossaryapp.models.ProductResults
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.row_subcategory_adapter.view.*
import java.lang.reflect.Method

class ProductDetailActivity : AppCompatActivity() {
    var product: Product? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        product = intent.getSerializableExtra(Product.KEY_PRODUCT) as Product
        init()
    }

    private fun init() {
//        setupToolBar()
        text_view_details_product_name.text = product?.productName
        text_view_details_description.text = product?.description
        text_view_details_price.text = product?.price.toString()
        Picasso.get()
            .load(Configure.IMAGE_URL + product?.image)
            .resize(200,200)
            .centerCrop()
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_didnt_load)
            .into(image_view_details_image)
    }

    //    private fun setupToolBar() {
//        var toolbar = toolbar
//        toolbar.title = product!!.productName
//        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            android.R.id.home -> finish()
//            R.id.action_cart -> Toast.makeText(applicationContext, "You just clicked on the Shopping Cart. Great work!", Toast.LENGTH_SHORT).show()
//            R.id.action_settings -> Toast.makeText(applicationContext, "You just clicked on Settings. Great work!", Toast.LENGTH_SHORT).show()
//            R.id.action_profile -> Toast.makeText(applicationContext, "You just clicked on Profile. Great work!", Toast.LENGTH_SHORT).show()
//        }
//        return true
//    }

}