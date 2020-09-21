package com.example.glossaryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        text_view_details_product_name.text = product?.productName
        text_view_details_price.text = product?.price.toString()
        Picasso.get()
            .load(Configure.IMAGE_URL + product?.image)
            .resize(200,200)
            .centerCrop()
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_didnt_load)
            .into(image_view_details_image)
    }

}