package com.example.pointatob

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.RadioButton
import android.widget.RadioGroup
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

        val radioSelection = findViewById<RadioGroup>(R.id.radio_preferences)
        val selected = radioSelection.checkedRadioButtonId
        val distance = intent.getStringExtra("distance")



        binding.pointA.text = intent.getStringExtra("pointA")
        binding.pointB.text = intent.getStringExtra("pointB")//intent.getStringExtra("distance")
    }

    fun sendPreference(view: View){

        val radioSelection = findViewById<RadioGroup>(R.id.radio_preferences)

        if (radioSelection.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please make a preference selection", Toast.LENGTH_SHORT).show()
            return
        }
        val distance = intent.getStringExtra("distance")
        val intent = Intent(this,ServiceListActivity::class.java).apply {
            putExtra("selectedButton", findViewById<RadioButton>(radioSelection.checkedRadioButtonId).text)
            putExtra("pointA", binding.pointA.text.toString())
            putExtra("pointB", binding.pointB.text.toString())
            putExtra("distance", distance);
        }
        startActivity(intent)
    }
}