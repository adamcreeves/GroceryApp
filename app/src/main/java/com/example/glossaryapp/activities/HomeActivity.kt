package com.example.glossaryapp.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
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
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.nav_header.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var myDrawerLayout: DrawerLayout
    lateinit var myNavView: NavigationView
    private lateinit var sessionManager: SessionManager
    lateinit var adapterCategory: AdapterCategory
    var myList: ArrayList<Category> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        setupToolbar()
        getData()
        myDrawerLayout = drawer_layout
        myNavView = nav_view

        var firstName = sessionManager.getFirstName()
        var userId = sessionManager.getUserId()
        var myHeaderView = myNavView.getHeaderView(0)

        myHeaderView.text_view_header_firstName.text = firstName
        myHeaderView.text_view_header_userId.text = userId

        var myToggle = ActionBarDrawerToggle(this, myDrawerLayout, toolbar, 0, 0)
        myDrawerLayout.addDrawerListener(myToggle)
        myToggle.syncState()
//        myNavView.setNavigationItemSelectedListener {
//
//        }


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
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_profile -> Toast.makeText(this, "You just clicked on your profile. Nice!", Toast.LENGTH_SHORT).show()
            R.id.item_address -> startActivity(Intent(applicationContext, AddressActivity::class.java))
            R.id.item_settings -> Toast.makeText(this, "You just clicked on settings. Nice!", Toast.LENGTH_SHORT).show()
            R.id.item_logout -> dialogLogout()
        }
        myDrawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {

        if(myDrawerLayout.isDrawerOpen(GravityCompat.START)){
            myDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun dialogLogout() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm log out")
        builder.setMessage("You really want to go?")
        builder.setNegativeButton("I guess not", object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, p1: Int) {
                dialog?.dismiss()
            }
        })
        builder.setPositiveButton("Yes, right now", object: DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                sessionManager.logout()
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }
        })
        var myAlertDialog = builder.create()
        myAlertDialog.show()
    }

}