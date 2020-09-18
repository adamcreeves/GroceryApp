package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.glossaryapp.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        init()
    }

    private fun init() {
        button_enter.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}