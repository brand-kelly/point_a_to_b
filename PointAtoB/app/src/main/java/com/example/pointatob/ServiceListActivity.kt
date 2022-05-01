package com.example.pointatob

import adapter.ItemAdapter
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pointatob.databinding.ActivityServiceListBinding
import data.Datasource

class ServiceListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_service_list)

        binding = ActivityServiceListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intent = intent

        val strPointA = intent.getStringExtra("pointA")
        val strPointB = intent.getStringExtra("pointB")
        binding.pointAView.text = intent.getStringExtra("selectedButton")
//        binding.pointB.text = intent.getStringExtra("pointB")


        val selected = intent.getStringExtra("selectedButton")
        val distance = intent.getStringExtra("distance")


        val myDataset = Datasource().loadServices(selected, distance)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, myDataset)

        recyclerView.setHasFixedSize(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}