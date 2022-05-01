package com.example.pointatob

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pointatob.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.maps.android.SphericalUtil;
import kotlin.properties.Delegates


@Suppress("DEPRECATION")
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var starting: LatLng
    private lateinit var destination: LatLng
    private var distance by Delegates.notNull<Double>()

    private var TAG = ".MapsActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check initialization of Places API
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        mapFragment.getMapAsync {
            val pointA = intent.getStringExtra("pointA")
            val pointB = intent.getStringExtra("pointB")
            val latA = intent.extras!!.getDouble("latA")
            val longA = intent.extras!!.getDouble("longA")
            starting = LatLng(latA, longA)
            val latB = intent.extras!!.getDouble("latB")
            val longB = intent.extras!!.getDouble("longB")
            destination = LatLng(latB, longB)
            val bounds = LatLngBounds.builder().include(starting).include(destination).build()
            distance = distanceInMi(latA, longA, latB, longB)

            // Add both markers and move the camera
            mMap = it
            mMap.addMarker(MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .position(starting)
                .title("Starting from: $pointA")
            )
            mMap.addMarker(MarkerOptions()
                .position(destination)
                .title("Destination: $pointB")
            )
            val urll = getDirectionURL(starting, destination, BuildConfig.MAPS_API_KEY)
            GetDirection(urll).execute()
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150))
        }

        val mainButton = findViewById<Button>(R.id.backToMainButton)
        val optionsButton = findViewById<Button>(R.id.toOptionsButton)

        mainButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        optionsButton.setOnClickListener{
            val intent = Intent(this, OptionsActivity::class.java).apply{
                putExtra("pointA", intent.getStringExtra("pointA"))
                putExtra("pointB", intent.getStringExtra("pointB"))
                putExtra("distance", distance)
            }
            startActivity(intent)
        }

    }

    fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
    fun distanceInMi(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val theta = lon1 - lon2
        var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta))
        dist = Math.acos(dist)
        dist = rad2deg(dist)
        dist = dist * 60 * 1.1515
        return dist
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



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Set lat and long values, place names
        val pointA = intent.getStringExtra("pointA")
        val pointB = intent.getStringExtra("pointB")
        val latA = intent.extras!!.getDouble("latA")
        val longA = intent.extras!!.getDouble("longA")
        val starting = LatLng(latA, longA)
        val latB = intent.extras!!.getDouble("latB")
        val longB = intent.extras!!.getDouble("longB")
        val destination = LatLng(latB, longB)

        mMap.clear()
        mMap.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .position(starting)
                .title(pointA)
                )
        mMap.addMarker(
            MarkerOptions()
            .position(destination)
            .title(pointB)
            )
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(starting, 14F))
    }

    private fun getDirectionURL(origin:LatLng, dest:LatLng, secret: String) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
                "&destination=${dest.latitude},${dest.longitude}" +
                "&sensor=false" +
                "&mode=driving" +
                "&key=$secret"
    }

    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()

            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data,MapData::class.java)
                val path =  ArrayList<LatLng>()
                for (i in 0 until respObj.routes[0].legs[0].steps.size){
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.GREEN)
                lineoption.geodesic(true)
            }
            mMap.addPolyline(lineoption)
        }
    }

    fun decodePolyline(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }



}
