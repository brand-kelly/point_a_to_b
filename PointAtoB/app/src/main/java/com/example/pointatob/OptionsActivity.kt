package com.example.pointatob

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.pointatob.databinding.ActivityOptionsBinding

class OptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        binding = ActivityOptionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = intent

        val strPointA = intent.getStringExtra("pointA")
        val strPointB = intent.getStringExtra("pointB")
        binding.pointA.text = intent.getStringExtra("pointA")
        binding.pointB.text = intent.getStringExtra("pointB")
    }

    fun sendPreference(view: View){
        val intent = Intent(this,ServiceListActivity::class.java).apply {
            putExtra("pointA", binding.pointA.text.toString())
            putExtra("pointB", binding.pointB.text.toString())
        }
        startActivity(intent)
    }
}