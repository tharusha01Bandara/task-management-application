package com.example.to_doapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Corrected layout file reference

        val button = findViewById<Button>(R.id.loginBtn)
        button.setOnClickListener {
            // Create an Intent to start TodoGetStart activity
            val intent = Intent(this, TodoGetStart::class.java)
            startActivity(intent)
        }
    }
}
