package com.example.glossaryapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.adapters.AdapterTabView
import com.example.glossaryapp.models.SubCategoriesResult
import com.example.glossaryapp.models.SubCategoryDataItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_category.*

class SubCategoryActivity : AppCompatActivity() {

    var myList: ArrayList<SubCategoryDataItem> = ArrayList()
    var adapterSubCategory: AdapterTabView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        var categoryId = intent.getIntExtra("CATID", 1).toString()

        init()
    }

    private fun init() {

        getData()
        adapterSubCategory = AdapterTabView(supportFragmentManager)
        view_pager.adapter = adapterSubCategory
        tab_layout.setupWithViewPager(view_pager)

    }

    private fun getData() {
        var url = "https://grocery-second-app.herokuapp.com/api/products"
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(Request.Method.GET, url, {
            var gson = Gson()
            var subCategoriesResults = gson.fromJson(it, SubCategoriesResult::class.java)
            myList.addAll(subCategoriesResults.data)
            for(i in 0 until myList.size) {
                adapterSubCategory?.addFragment(myList[i].subName, myList[i].subId)
            }
            adapterSubCategory?.dataChange()
            Toast.makeText(this, "Products are Loading...", Toast.LENGTH_SHORT).show()
        },
            {

            })
        requestQueue.add(request)
    }
}
