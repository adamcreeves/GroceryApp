package com.example.glossaryapp.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.app.Endpoints
import com.example.glossaryapp.helpers.SessionManager
import com.example.glossaryapp.models.LoginResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        setupToolBar()
        button_login_to_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        button_login_submit.setOnClickListener {
            var loginEmail = edit_text_login_email.text.toString()
            var loginPassword = edit_text_login_password.text.toString()


            var params = HashMap<String, String>()
            params["email"] = loginEmail
            params["password"] = loginPassword
            var jsonObject = JSONObject(params as Map<*, *>)
            var editor: SharedPreferences.Editor? = null
            var request = JsonObjectRequest(
                Request.Method.POST, Endpoints.getLogin(), jsonObject, {
                    val gson = Gson()
                    var loginResponse = gson.fromJson(it.toString(), LoginResponse::class.java)
                    sessionManager.saveUserInfo(loginResponse.user)
                    sessionManager.saveUserLogin(loginResponse.token)
                    Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this, HomeActivity::class.java))
                },
                {
                    Toast.makeText(
                        applicationContext,
                        "Username or password incorrect",
                        Toast.LENGTH_SHORT
                    ).show()
                })
//

            Volley.newRequestQueue(this).add(request)
        }
    }

    private fun setupToolBar() {
        var toolbar = toolbar
        toolbar.title = "Login"
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