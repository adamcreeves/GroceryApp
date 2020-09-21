package com.example.glossaryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.glossaryapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.row_subcategory_adapter.view.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        init()
    }

    private fun init() {
        var productName = intent.getStringExtra("PRODUCT_NAME")
        var price = intent.getStringExtra("PRICE")
        var image = intent.getStringExtra("IMAGE")
        var imageUrl = "http://rjtmobile.com/grocery/images/"
        text_view_details_product_name.text = productName
        text_view_details_price.text = price
        Picasso.get()
            .load(imageUrl + image)
            .resize(350,350)
            .centerCrop()
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_didnt_load)
            .into(image_view_details_image)
    }
}