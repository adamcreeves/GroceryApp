package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.adapters.AdapterCategory
import com.example.glossaryapp.app.Endpoints
import com.example.glossaryapp.models.Category
import com.example.glossaryapp.models.CategoryResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    var myList: ArrayList<Category> = ArrayList()
    lateinit var adapterCategory: AdapterCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    private fun init() {
        getData()

        adapterCategory = AdapterCategory(this)
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.adapter = adapterCategory

        image_view_home_headline_image.setImageResource(R.drawable.app_logo)
    }

    private fun getData() {
        var request = StringRequest(Request.Method.GET, Endpoints.getCategory(), {
            var gson = Gson()
            var categoryResult = gson.fromJson(it, CategoryResult::class.java)
            adapterCategory.setData(categoryResult.data)

            Toast.makeText(applicationContext, "Category Images are Loading...", Toast.LENGTH_SHORT).show()
            progress_bar.visibility = View.GONE
        },
            {

            })
        Volley.newRequestQueue(this).add(request)
    }
}