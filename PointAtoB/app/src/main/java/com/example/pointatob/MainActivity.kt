package com.example.pointatob

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.pointatob.databinding.ActivityMainBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MainActivity : AppCompatActivity() {
    // Global variables
    private lateinit var binding: ActivityMainBinding
    private val placeNames = mutableListOf<String>("", "")
    private val lats = mutableListOf<Double>(0.0, 0.0)
    private val longs = mutableListOf<Double>(0.0, 0.0)
    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        // Places initialization
        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        val placesClient = Places.createClient(this)
        val autocompleteFragmentA =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment_a)
                    as AutocompleteSupportFragment
        val autocompleteFragmentB =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment_b)
                    as AutocompleteSupportFragment

        // Set AutoComplete fragment hints
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
                place.name?.let { placeNames[0] = (it.toString()) }
                place.latLng.longitude?.let { longs[0] = it}
                place.latLng.latitude?.let { lats[0] = it}
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
                place.name?.let { placeNames[1] = (it.toString()) }
                place.latLng.longitude?.let { longs[1] = it}
                place.latLng.latitude?.let { lats[1] = it}
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