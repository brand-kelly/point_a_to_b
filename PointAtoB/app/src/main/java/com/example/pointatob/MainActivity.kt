package com.example.pointatob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.pointatob.databinding.ActivityMainBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val placeNames = mutableListOf<String>()
    private val TAG: String = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        // Places initialization
        // TODO: Remove API Key
        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        val placesClient = Places.createClient(this)
        val autocompleteFragmentA =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment_a)
                    as AutocompleteSupportFragment
        val autocompleteFragmentB =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment_b)
                    as AutocompleteSupportFragment
        autocompleteFragmentA.setHint("Pick Up Location")
        autocompleteFragmentB.setHint("Destination")
        // Set Place Fields for required fields
        autocompleteFragmentA.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )
        autocompleteFragmentB.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragmentA.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                placeNames.add(place.name)
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })

        autocompleteFragmentB.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                placeNames.add(place.name)
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
//        setContentView(view)
    }


    fun sendData(view: View) {
//        if (binding.pointB.text.toString() == "") {
//            Toast.makeText(this, "Please input a starting address", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        if (binding.pointA.text.toString() == "") {
//            Toast.makeText(this, "Please input a destination address", Toast.LENGTH_SHORT).show()
//            return
//        }

        val intent = Intent(this, OptionsActivity::class.java).apply {
            putExtra("pointA", placeNames[0])
            putExtra("pointB", placeNames[1])
        }
        startActivity(intent)
    }

}