package huji.post_pc.path2pet
import android.Manifest
import android.app.Activity
import android.location.Address
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
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
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.*


class Fragment_b_Map : Fragment() {
    private lateinit var mMap: GoogleMap
    private lateinit var myContext: Context
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLocation: LatLng
    private lateinit var lostPetActivityInstance: LostPetProcess
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
        val view = inflater.inflate(R.layout.fragment_b_map, container, false)
        myContext = view.context
        lostPetActivityInstance = activity as LostPetProcess

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        var searchView: SearchView = view.findViewById(R.id.idSearchView)
        val geocoder : Geocoder


//        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(myContext)
//        checkLocationPermission()


        // TODO - try to ask permissions
        if (ContextCompat.checkSelfPermission(myContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) !==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(lostPetActivityInstance!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(lostPetActivityInstance,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            } else {
                ActivityCompat.requestPermissions(lostPetActivityInstance,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            }
        }
        // TODO - end of try to ask permissions

//
//
        //google map
        val mapFragment:SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            // todo - make map go to your current location
            mMap = it

            val point = CameraUpdateFactory.newLatLng(LatLng(32.109333, 34.855499))
            val latLng = LatLng(32.109333, 34.855499)
            val home = mMap.addMarker(MarkerOptions().position(latLng).title("Home"))
            home.tag = -1
            // moves camera to coordinates
            mMap.moveCamera(point)
            // animates camera to coordinates
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f))

            it.setOnMapClickListener { coordinate->
                it.clear()
                it.addMarker(MarkerOptions().position(coordinate))
            }

        })

        //TODO - add location to SP (key = AppPath2Pet.SP_LOCATION)

//         search view query
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
                    val latLng = LatLng(address.latitude, address.longitude)
                    mMap.clear()
                    mMap.addMarker(MarkerOptions().position(latLng).title(searchInitLocation))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                }
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false

            }
        })

        // next listener
        nextButton.setOnClickListener {
            lostPetActivityInstance.progressBar.incrementProgressBy(1)
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            lostPetActivityInstance.onBackPressed()
        }

//        // todo - places suggestions
//        val autocompleteFragment = childFragmentManager.fragments[1] as AutocompleteSupportFragment
//        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
//        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onPlaceSelected(place: Place) {
//                val latLng = place.latLng
//                if (latLng != null) {
//                    mMap.addMarker(MarkerOptions().position(latLng).title(place.name))
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
//                }
//            }
//            override fun onError(status: Status) {
//                Log.i(TAG, "Google maps: places suggestions: An error occurred: $status")
//            }
//        })


        return view
    }

    private fun nextButtonOnClick(view:View) {
        Navigation.findNavController(view).navigate(R.id.fragmentTypeSex)
    }

    // ask permissions code

//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
//                                            grantResults: IntArray) {
//        when (requestCode) {
//            1 -> {
//                if (grantResults.isNotEmpty() && grantResults[0] ==
//                    PackageManager.PERMISSION_GRANTED) {
//                    if ((ContextCompat.checkSelfPermission(myContext,
//                            Manifest.permission.ACCESS_FINE_LOCATION) ===
//                                PackageManager.PERMISSION_GRANTED)) {
//                        Toast.makeText(myContext, "Permission Granted", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    Toast.makeText(myContext, "Permission Denied", Toast.LENGTH_SHORT).show()
//                }
//                return
//            }
//        }
//    }

//    override fun onResume() {
//        super.onResume()
//        if (ContextCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED) {
//
//            fusedLocationProvider?.requestLocationUpdates(
//                locationRequest,
//                locationCallback,
//                Looper.getMainLooper()
//            )
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if (ContextCompat.checkSelfPermission(myContext,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//            == PackageManager.PERMISSION_GRANTED) {
//
//            fusedLocationProvider?.removeLocationUpdates(locationCallback)
//        }
//    }
//
//    private fun checkLocationPermission() {
//        if (ActivityCompat.checkSelfPermission(
//                myContext,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                    lostPetActivityInstance,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                )
//            ) {
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//                AlertDialog.Builder(myContext)
//                    .setTitle("Location Permission Needed")
//                    .setMessage("This app needs the Location permission, please accept to use location functionality")
//                    .setPositiveButton(
//                        "OK"
//                    ) { _, _ ->
//                        //Prompt the user once explanation has been shown
//                        requestLocationPermission()
//                    }
//                    .create()
//                    .show()
//            } else {
//                // No explanation needed, we can request the permission.
//                requestLocationPermission()
//            }
//        }
//    }
//
//    private fun requestLocationPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            ActivityCompat.requestPermissions(
//                lostPetActivityInstance,
//                arrayOf(
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
//                ),
//                MY_PERMISSIONS_REQUEST_LOCATION
//            )
//        } else {
//            ActivityCompat.requestPermissions(
//                lostPetActivityInstance,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                MY_PERMISSIONS_REQUEST_LOCATION
//            )
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            MY_PERMISSIONS_REQUEST_LOCATION -> {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // location-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(
//                            myContext,
//                            Manifest.permission.ACCESS_FINE_LOCATION
//                        ) == PackageManager.PERMISSION_GRANTED
//                    ) {
//                        fusedLocationProvider?.requestLocationUpdates(
//                            locationRequest,
//                            locationCallback,
//                            Looper.getMainLooper()
//                        )
//                    }
//
//                } else {
//
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                    Toast.makeText(myContext, "permission denied", Toast.LENGTH_LONG).show()
//                }
//                return
//            }
//        }
//    }
//
//    companion object {
//        private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
//    }

}