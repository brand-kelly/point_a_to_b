package com.example.pointatob

import adapter.ItemAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.pointatob.databinding.ActivityMainBinding
import com.example.pointatob.databinding.ActivityServiceListBinding
import data.Datasource

class ServiceListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


        val myDataset = Datasource().loadServices(selected)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = ItemAdapter(this, myDataset)

        recyclerView.setHasFixedSize(true)
    }


}