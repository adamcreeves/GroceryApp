package com.example.glossaryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.glossaryapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.row_category_adapter.view.*

class SubCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        init()
    }

    private fun init() {
        var catId = intent.getStringExtra("CAT_ID")?.toInt()
        var _id = intent.getStringExtra("_ID")
        var catName = intent.getStringExtra("CAT_NAME")
        var slug = intent.getStringExtra("SLUG")
        var catImage = intent.getStringExtra("CAT_IMAGE")
        var imageUrl = "http://rjtmobile.com/grocery/images/"
        text_view_category_title.text = catName
        Picasso.get()
            .load(imageUrl + catImage)
            .resize(300,300)
            .centerCrop()
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_didnt_load)
            .into(image_view_subcategory)
    }
}


//"CAT_ID", category.catId)
//intent.putExtra("_ID", category._id)
//intent.putExtra("CAT_NAME", category.catName)
//intent.putExtra("SLUG", category.slug)
//intent.putExtra("CAT_IMAGE", category.catImage)