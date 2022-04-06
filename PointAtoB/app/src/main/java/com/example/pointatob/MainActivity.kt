package com.example.pointatob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.pointatob.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun sendData(view: View){
        val intent = Intent(this,OptionsActivity::class.java).apply {
            putExtra("pointA", binding.pointA.text.toString())
            putExtra("pointB", binding.pointB.text.toString())
        }
        startActivity(intent)
    }
}