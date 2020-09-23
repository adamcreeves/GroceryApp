package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.glossaryapp.R
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.app_bar.*

class AddAddressActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        init()
    }

    private fun setupToolBar() {
        var toolbar = toolbar
        toolbar.title = "Add New Address"
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
        }
        return true
    }

    private fun init() {

        button_save_address.setOnClickListener {
            var street = edit_text_street.text.toString()
            var city = edit_text_address_city.text.toString()
            var state = edit_text_address_state.text.toString()
            var zip = edit_text_address_zip.text.toString()
            var country = edit_text_address_country.text.toString()

            var params = HashMap<String, String>()
            params

            Toast.makeText(applicationContext, "New employee record successfully added!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, AddressActivity::class.java))
        }
    }
}