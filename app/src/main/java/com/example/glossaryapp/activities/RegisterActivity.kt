package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {

//
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
            var url = "https://grocery-second-app.herokuapp.com/api/auth/register"
            if(password == confirmPassword){
                var request = JsonObjectRequest(
                    Request.Method.POST, url, jsonObject, {
                    Toast.makeText(applicationContext, "Registration successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                },
                    {

                    })
                Volley.newRequestQueue(this).add(request)
            } else {
                Toast.makeText(applicationContext, "Your password doesn't match", Toast.LENGTH_SHORT).show()
            }
        }
    }
}