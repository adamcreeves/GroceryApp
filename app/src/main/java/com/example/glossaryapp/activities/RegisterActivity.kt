package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.app.Endpoints
import com.example.glossaryapp.helpers.SessionManager
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        setupToolBar()
        button_register_to_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        button_register_submit.setOnClickListener {
            var firstName = edit_text_register_firstName.text.toString()
            var email = edit_text_register_email.text.toString()
            var password = edit_text_register_password.text.toString()
            var confirmPassword = edit_text_register_confirm_password.text.toString()
            var mobile = edit_text_register_mobile.text.toString()

            var params = HashMap<String, String>()
            params["firstName"] = firstName
            params["email"] = email
            params["password"] = password
            params["mobile"] = mobile

            var jsonObject = JSONObject(params as Map<*, *>)
            if (password == confirmPassword) {
                var request = JsonObjectRequest(
                    Request.Method.POST, Endpoints.getRegister(), jsonObject, {
                        Toast.makeText(
                            applicationContext,
                            "Registration successful",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    },
                    {

                    }
                )
                Volley.newRequestQueue(this).add(request)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Your password doesn't match",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupToolBar() {
        var toolbar = toolbar
        toolbar.title = "Register"
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
                Toast.makeText(applicationContext, "You're already logged out, that's why you're here", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return true
    }

}

//    user id: 5f64cb7eaf18dc0017608550