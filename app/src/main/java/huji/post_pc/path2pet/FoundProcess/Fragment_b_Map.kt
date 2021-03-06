package huji.post_pc.path2pet.FoundProcess
import android.Manifest
import android.location.Address
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.model.LatLng

import android.location.Geocoder
import androidx.appcompat.widget.SearchView
import java.lang.Exception
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import huji.post_pc.path2pet.AppPath2Pet
import huji.post_pc.path2pet.R


class Fragment_b_Map : Fragment() {
    private lateinit var mMap: GoogleMap
    private lateinit var myContext: Context
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLocation: LatLng
    private lateinit var foundPetActivityInstance: FoundPetProcess
    private var fusedLocationProvider: FusedLocationProviderClient? = null
    private val locationRequest: LocationRequest =  LocationRequest.create().apply {
        interval = 30
        fastestInterval = 10
        priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        maxWaitTime= 60
    }

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                //The last location in the list is the newest
                val location = locationList.last()
                Toast.makeText(
                    myContext,
                    "Got Location: " + location.toString(),
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.found_fragment_b_map, container, false)
        myContext = view.context
        foundPetActivityInstance = activity as FoundPetProcess
        // initial latlng for Tel Aviv
        var latLng : LatLng = LatLng(32.109333, 34.855499)
        var latitude: String = "32.109333"
        var longitude : String = "34.855499"


        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val searchView: SearchView = view.findViewById(R.id.idSearchView)

        // upload data from sp
        latitude = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_LATITUDE, latitude).toString()
        longitude = foundPetActivityInstance.sp.getString(AppPath2Pet.SP_LONGITUDE, longitude).toString()
        latLng = LatLng(latitude.toDouble(), longitude.toDouble())

        // google map
        val mapFragment:SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            mMap = it

            val point = CameraUpdateFactory.newLatLng(latLng)
            val home = mMap.addMarker(MarkerOptions().position(latLng).title("Home"))
            home.tag = -1
            // moves camera to coordinates
            mMap.moveCamera(point)
            // animates camera to coordinates
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))

            it.setOnMapClickListener { coordinate->
                it.clear()
                it.addMarker(MarkerOptions().position(coordinate))
                latLng = LatLng(coordinate.latitude, coordinate.longitude)
            }

        })

        // search view query
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                val searchInitLocation = searchView.query.toString()
                var addressList: List<Address>?
                if (searchInitLocation != "") {
                    val geocoder = Geocoder(view.context)
                    try {
                        addressList = geocoder.getFromLocationName(searchInitLocation, 1)
                        if (addressList.isEmpty()) {
                            throw Exception()
                        }
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                        return false
                    }
                    val address: Address = addressList!![0]
                    latLng = LatLng(address.latitude, address.longitude)
                    mMap.clear()
                    mMap.addMarker(MarkerOptions().position(latLng).title(searchInitLocation))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                }
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false

            }
        })

        // next listener
        nextButton.setOnClickListener {
            foundPetActivityInstance.progressBar.incrementProgressBy(1)
            with(foundPetActivityInstance.sp.edit()) {
                putString(AppPath2Pet.SP_LATITUDE, latLng.latitude.toString())
                putString(AppPath2Pet.SP_LONGITUDE, latLng.longitude.toString())
                apply()
            }
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            foundPetActivityInstance.onBackPressed()
        }

        return view
    }

    private fun nextButtonOnClick(view:View) {
        Navigation.findNavController(view).navigate(R.id.fragment_c_TypeAndSex)
    }
}