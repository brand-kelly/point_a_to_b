package com.example.pointatob

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pointatob.databinding.ActivityMainBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.gms.location.*


class MainActivity : AppCompatActivity() {
    // Global variables
    private lateinit var binding: ActivityMainBinding
    private val placeNames = mutableListOf<String>("", "")
    private val lats = mutableListOf<Double>(0.0, 0.0)
    private val longs = mutableListOf<Double>(0.0, 0.0)
    private val TAG: String = "MainActivity"
    private var locationPermissionGranted = false
    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    private lateinit var locationManager: LocationManager
    private val locationPermissionCode = 2
    val PERMISSION_ID = 42
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        //current location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        // Places initialization
        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
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
                place.latLng?.longitude.let {
                    if (it != null) {
                        longs[0] = it
                    }
                }
                place.latLng?.latitude.let {
                    if (it != null) {
                        lats[0] = it
                    }
                }
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
                place.latLng?.longitude.let {
                    if (it != null) {
                        longs[1] = it
                    }
                }
                place.latLng?.latitude.let {
                    if (it != null) {
                        lats[1] = it
                    }
                }
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
//        setContentView(view)
    }

    @SuppressLint("MissingPermission")
    fun currentLocation(view: View){
        getLocationPermission()
        if (locationPermissionGranted) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    // Got last known location. In some rare situations this can be null.
                    findViewById<TextView>(R.id.latTextView).text = location?.latitude.toString()
                    findViewById<TextView>(R.id.lonTextView).text = location?.longitude.toString()
                }
            Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show()

        }else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.")
            // Prompt the user for permission.
            getLocationPermission()
        }
    }



    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        Toast.makeText(this, "location permission granted", Toast.LENGTH_SHORT).show()
    }






    fun sendData(view: View) {
        if (placeNames[0] == "") {
            Toast.makeText(this, "Please input a starting address", Toast.LENGTH_SHORT).show()
            return
        }

        if (placeNames[1] == "") {
            Toast.makeText(this, "Please input a destination address", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, MapsActivity::class.java).apply {
            putExtra("pointA", placeNames[0])
            putExtra("pointB", placeNames[1])
            putExtra("longA", longs[0])
            putExtra("longB", longs[1])
            putExtra("latA", lats[0])
            putExtra("latB", lats[1])
        }
        startActivity(intent)
    }

}