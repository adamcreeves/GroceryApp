package com.example.glossaryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.adapters.AdapterSubCategory
import com.example.glossaryapp.models.Product
import com.example.glossaryapp.models.ProductData
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_category.*

class SubCategoryActivity : AppCompatActivity() {

    var myList: ArrayList<ProductData> = ArrayList()
    var adapterSubCategory: AdapterSubCategory? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        init()
    }

    private fun init() {

        getData()

    }

    private fun getData() {
        var url = "https://grocery-second-app.herokuapp.com/api/products"
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(Request.Method.GET, url, {
            var gson = Gson()
            var products = gson.fromJson(it, Product::class.java)
            myList.addAll(products.data)
            adapterSubCategory?.setData(myList)
            adapterSubCategory = AdapterSubCategory(this, myList)
            recycler_view.layoutManager = LinearLayoutManager(this)
            recycler_view.adapter = adapterSubCategory
            Toast.makeText(this, "Products are Loading...", Toast.LENGTH_SHORT).show()
        },
            {

            })
        requestQueue.add(request)
    }
}
