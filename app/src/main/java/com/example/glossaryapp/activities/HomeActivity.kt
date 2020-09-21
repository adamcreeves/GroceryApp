package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.adapters.AdapterCategory
import com.example.glossaryapp.models.CategoryDataItem
import com.example.glossaryapp.models.CategoryResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    var myList: ArrayList<CategoryDataItem> = ArrayList()
    var adapterCategory: AdapterCategory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    private fun init() {
        image_view_home_headline_image.setImageResource(R.drawable.app_logo)
        getData()
        button_debug.setOnClickListener {
            startActivity(Intent(this, SubCategoryActivity::class.java))
        }
    }

    private fun getData() {
        var url = "https://grocery-second-app.herokuapp.com/api/category"
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(Request.Method.GET, url, {
            var gson = Gson()
            var categoryResult = gson.fromJson(it, CategoryResult::class.java)

            myList.addAll(categoryResult.data)
            adapterCategory?.setData(myList)
            adapterCategory = AdapterCategory(this, myList)
            recycler_view.layoutManager = GridLayoutManager(this, 2)
            recycler_view.adapter = adapterCategory
            Toast.makeText(applicationContext, "Category Images are Loading...", Toast.LENGTH_SHORT).show()
            progress_bar.visibility = View.GONE
        },
            {

            })
        requestQueue.add(request)
    }
}