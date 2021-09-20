package huji.post_pc.path2pet
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
import com.google.android.gms.location.FusedLocationProviderClient


class Fragment_b_Map : Fragment() {
    private lateinit var mMap: GoogleMap
    private lateinit var myContext: Context
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLocation: LatLng

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_b_map, container, false)
        myContext = view.context
        val lostPetActivityInstance: LostPetProcess? = activity as LostPetProcess?

        // find views
        val nextButton: Button = view.findViewById(R.id.next)
        val prevButton: Button = view.findViewById(R.id.previous)
        val searchView: SearchView = view.findViewById(R.id.idSearchView)
//        val autocompleteFragment = childFragmentManager.fragments[1] as AutocompleteSupportFragment

        //google map
        val mapFragment:SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            mMap = it
            it.setOnMapClickListener { coordinate->
                it.clear()
                it.addMarker(MarkerOptions().position(coordinate))
          }
        })

        //TODO - add location to SP (key = AppPath2Pet.SP_LOCATION)

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
                    val latLng = LatLng(address.latitude, address.longitude)
                    mMap.addMarker(MarkerOptions().position(latLng).title(searchInitLocation))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                }
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {

                return false

            }
        })

//        // places suggestions
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

        // next listener
        nextButton.setOnClickListener {
            lostPetActivityInstance?.progressBar?.incrementProgressBy(1)
            nextButtonOnClick(it)
        }

        // prev listener
        prevButton.setOnClickListener {
            lostPetActivityInstance!!.onBackPressed()
        }
        return view
    }

    private fun nextButtonOnClick(view:View) {
        Navigation.findNavController(view).navigate(R.id.fragmentTypeSex)
    }
}