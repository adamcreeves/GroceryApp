package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.glossaryapp.R
import com.example.glossaryapp.helpers.SessionManager
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        sessionManager = SessionManager(this)
        init()
    }

    private fun init() {
        if (sessionManager.getQuickLogin()) {
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        } else {
            button_enter.setOnClickListener {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}