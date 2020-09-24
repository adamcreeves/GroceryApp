package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.glossaryapp.R
import com.example.glossaryapp.helpers.SessionManager
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.app_bar.*

class AddressActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        sessionManager = SessionManager(this)
        init()
    }

    private fun setupToolBar() {
        var toolbar = toolbar
        toolbar.title = "Select Shipping Address"
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
            R.id.action_home -> startActivity(
                Intent(
                    applicationContext,
                    HomeActivity::class.java
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
                startActivity(Intent(applicationContext, StartActivity::class.java))
            }
        }
        return true
    }

    private fun init() {
        setupToolBar()
        button_add_new_address.setOnClickListener {
            startActivity(Intent(applicationContext, AddAddressActivity::class.java))
        }
    }
}