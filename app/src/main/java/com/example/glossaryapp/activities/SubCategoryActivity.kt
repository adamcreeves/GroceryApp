package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.adapters.AdapterFragment
import com.example.glossaryapp.app.Endpoints
import com.example.glossaryapp.helpers.SessionManager
import com.example.glossaryapp.models.Category
import com.example.glossaryapp.models.SubCategoriesResult
import com.example.glossaryapp.models.SubCategory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*

class SubCategoryActivity : AppCompatActivity() {

    var myList: ArrayList<SubCategory> = ArrayList()
    lateinit var adapterFragment: AdapterFragment
    lateinit var sessionManager: SessionManager
    var category: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        sessionManager = SessionManager(this)
        category = intent.getSerializableExtra(Category.KEY_CATEGORY) as Category
        init()
    }

    private fun setupToolBar() {
        var toolbar = toolbar
        toolbar.title = category!!.catName
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_cart -> startActivity(
                Intent(
                    applicationContext,
                    ShoppingCartActivity::class.java
                )
            )
        }
        return true
    }

    private fun init() {
        setupToolBar()
        getData(category!!.catId)
        adapterFragment = AdapterFragment(supportFragmentManager)

    }

    private fun getData(catId: Int) {
        var request = StringRequest(Request.Method.GET, Endpoints.getSubcategoryByCatId(catId), {
            var gson = Gson()
            var subCategoriesResults = gson.fromJson(it, SubCategoriesResult::class.java)
            myList.addAll(subCategoriesResults.data)
            for (i in 0 until myList.size) {
                adapterFragment.addFragment(myList[i])
            }
            Toast.makeText(this, "Products are Loading...", Toast.LENGTH_SHORT).show()
            view_pager_subCatActivity.adapter = adapterFragment
            tab_layout.setupWithViewPager(view_pager_subCatActivity)

        },
            {

            })
        Volley.newRequestQueue(this).add(request)

    }
}
