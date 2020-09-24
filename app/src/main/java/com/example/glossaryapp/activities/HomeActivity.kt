package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import com.example.glossaryapp.helpers.SessionManager
import com.example.glossaryapp.models.Category
import com.example.glossaryapp.models.CategoryResult
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar.*

class HomeActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    lateinit var adapterCategory: AdapterCategory
    var myList: ArrayList<Category> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        sessionManager = SessionManager(this)
        init()
    }

    private fun setupToolbar() {
        var toolbar = toolbar
        toolbar.title = "RäGN Home"
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_cart -> startActivity(
                Intent(
                    applicationContext,
                    ShoppingCartActivity::class.java
                )
            )
            R.id.action_settings -> Toast.makeText(
                applicationContext,
                "You just clicked on Settings. Great work!",
                Toast.LENGTH_SHORT
            ).show()
            R.id.action_profile -> Toast.makeText(
                applicationContext,
                "You just clicked on Profile. Great work!",
                Toast.LENGTH_SHORT
            ).show()
            R.id.action_logout -> {
                Toast.makeText(applicationContext, "You just logged out :(", Toast.LENGTH_SHORT).show()
                sessionManager.logout()
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }
        }
        return true
    }

    private fun init() {
        setupToolbar()
        getData()
        adapterCategory = AdapterCategory(this)
        recycler_view.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.adapter = adapterCategory

        image_view_home_headline_image.setImageResource(R.drawable.app_logo)
    }


    private fun getData() {
        var request = StringRequest(Request.Method.GET, Endpoints.getCategory(), {
            var gson = Gson()
            var categoryResult = gson.fromJson(it, CategoryResult::class.java)
            adapterCategory.setData(categoryResult.data)

            Toast.makeText(
                applicationContext,
                "Category Images are Loading...",
                Toast.LENGTH_SHORT
            ).show()
            progress_bar.visibility = View.GONE
        },
            {

            })
        Volley.newRequestQueue(this).add(request)
    }
}