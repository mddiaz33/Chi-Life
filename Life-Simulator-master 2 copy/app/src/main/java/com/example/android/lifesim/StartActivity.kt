package com.example.android.lifesim

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    // Button click starts input Activity
    fun inputScreenActivity(view: View) {
        val intent = Intent(this, inputActivity::class.java)
        startActivity(intent)
        finish()

    }
}
