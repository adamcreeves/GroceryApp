package com.example.glossaryapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.models.User
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
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
            var url = "https://grocery-second-app.herokuapp.com/api/auth/login"

            var request = JsonObjectRequest(
                Request.Method.POST, url, jsonObject, {
                    startActivity(Intent(this, HomeActivity::class.java))
                    val FILE_NAME = "REGISTERED_USERS_TOKENS"
                    var KEY_TOKEN = "token"
                    var sharedPreferences = this.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
                    var editor = sharedPreferences.edit()
                    var gson = Gson()
//                    var tokenResult = gson.fromJson(it, User::class.java)
//                    editor.putString(KEY_TOKEN, tokenResult[token])
                    Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()

            },
                {
                    Toast.makeText(applicationContext, "Username or password incorrect", Toast.LENGTH_SHORT).show()
                })
            Volley.newRequestQueue(this).add(request)
        }
    }
}