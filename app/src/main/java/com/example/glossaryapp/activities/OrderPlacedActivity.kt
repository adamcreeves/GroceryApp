package com.example.glossaryapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.glossaryapp.R
import com.example.glossaryapp.database.DBHelper

class OrderPlacedActivity : AppCompatActivity() {
    private val delayedTime: Long = 2000
    lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_placed)
        var handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
        }, delayedTime)
    }
}