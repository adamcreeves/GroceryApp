package com.example.glossaryapp.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
                    Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()
                    Log.d("abc", sessionManager.getUserInfo().toString())
                    startActivity(Intent(this, HomeActivity::class.java))
            },
                {
                    Toast.makeText(applicationContext, "Username or password incorrect", Toast.LENGTH_SHORT).show()
                })
//

            Volley.newRequestQueue(this).add(request)
        }
    }
}