package com.hirepedal.customer.dashboard

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.hirepedal.customer.R
import com.hirepedal.customer.activities.RootActivity
import com.hirepedal.customer.base.BaseFragment
import com.hirepedal.customer.cart.CartFragment
import com.hirepedal.customer.cart.CartItem
import com.hirepedal.customer.utils.sharedpreference.SharedPreferenceManager
import java.io.IOException
import java.util.*


class DashboardFragment : BaseFragment(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mGoogleApiClient: GoogleApiClient
    private var mLocationManager: LocationManager? = null
    private lateinit var map: GoogleMap
    private lateinit var mMapFragment: SupportMapFragment
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false
    private var cartItemList = ArrayList<CartItem>()

    private fun initializePlaces(){

        // Hercules
        cartItemList.add(CartItem("Sangam Cycle Shop", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Roadsters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.9510036, 75.7577134))
        cartItemList.add(CartItem("Bumson Saddle Shop", 2, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Roadeo", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.903857, 75.821208))
        cartItemList.add(CartItem("Jayant Cycles", 3, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Ryders", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.9359042, 75.7114598))
        cartItemList.add(CartItem("Altaf Cycles", 4, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Turbodrive MTB", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.996827, 75.902208))
        cartItemList.add(CartItem("Five Start Cycles", 5, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "CMX", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.829615, 75.644113))

        // BSA
        cartItemList.add(CartItem("Sai Cycles", 6, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Champ", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.943993, 75.809052))
        cartItemList.add(CartItem("VST Cycles", 7, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Toddlers", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.996200, 75.828222))
        cartItemList.add(CartItem("Aabheer Cycles", 7, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Ladybird", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.938494, 75.632905))
        cartItemList.add(CartItem("Chitranjan Cycles", 8, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Workouts", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.929836, 75.840632))
        cartItemList.add(CartItem("Gajendra Cycles", 9, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Jr. Roadsters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                27.030329, 75.623733))
        cartItemList.add(CartItem("Kanhaiya Lal Cycles", 10, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Roadsters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.898083, 75.895666))



        // Hero Cycles
        cartItemList.add(CartItem("Madhukar Cycles", 11, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Maxim Fun Series", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.898083, 75.752146))

        cartItemList.add(CartItem("Radheyshyam Cycles", 12, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Special Kidz Bikes", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.913960, 75.839553))
        cartItemList.add(CartItem("Subhash Cycles", 13, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Super Start Series", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                27.066849, 75.602690))
        cartItemList.add(CartItem("Vikrant Cycles", 14, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "SLR", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.903291, 75.812023))
        cartItemList.add(CartItem("Vivek Cycles", 15, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Ranger MTB/ATB", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.9068372, 75.7992979))
        cartItemList.add(CartItem("Purshottam Cycles", 16, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "City Bikes", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.872220, 75.812781))
        cartItemList.add(CartItem("Jawahar Cycles", 17, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Roadsters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.929565, 75.757142))
        cartItemList.add(CartItem("Hanumant Cycles", 18, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Hero Sprint", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.961072, 75.846072))
        cartItemList.add(CartItem("Hariram Cycles", 19, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "ATB", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.928752, 26.928752))
        cartItemList.add(CartItem("Amanpreet Cycles", 19, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Master Blasters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.880966, 75.752126))


    }


    private fun placeCyclesOnMarker() {
        for (cartItem in cartItemList) {
            val currentLatLng = LatLng(cartItem?.pLat!!, cartItem?.pLong!!)
            val markerOptions = MarkerOptions().position(currentLatLng).title(cartItem.partnerName)
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.drawable.ic_cycle)))
            map.addMarker(markerOptions)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        initializeGoogleAPIClient()



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                lastLocation = p0.lastLocation
                placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
            }
        }
        createLocationRequest()
    }

    override fun onMarkerClick(p0: Marker?) = false

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setUpMap()
        initializePlaces()
        placeCyclesOnMarker()
    }

    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location).title("Your Location")
        val titleStr = getAddress(location)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.drawable.ic_user_location)))
        //  markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        //  markerOptions.title(titleStr)
        map.addMarker(markerOptions)

    }

    private fun getAddress(latLng: LatLng): String {

        val geocoder = Geocoder(context)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                for (i in 0 until address.maxAddressLineIndex) {
                    addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
                }
            }
        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }

        return addressText
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
        private const val PLACE_PICKER_REQUEST = 3
        private const val MY_PERMISSION_REQUEST_READ_LOCATION = 4
    }


    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context!!,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    DashboardFragment.LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }


    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(context!!)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(activity,
                            DashboardFragment.REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_search, null)
        bindViews(v)
        attachListeners()
        mMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mMapFragment.getMapAsync(this)


        return v
    }

    override fun bindViews(view: View?) {
        super.bindViews(view)
        val fab = view!!.findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            loadPlacePicker()
        }
    }

    private fun setUpMap() {

        if (ActivityCompat.checkSelfPermission(context!!,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), DashboardFragment.LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_TERRAIN
        //GoogleMap.MAP_TYPE_NORMAL
        //GoogleMap.MAP_TYPE_SATELLITE
        //GoogleMap.MAP_TYPE_HYBRID

        // Rajasthan - Jaipur : 26.9134799,75.7293832

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_cart,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.cart -> {
                var cartFragment : CartFragment = CartFragment()
                showFragment(cartFragment)
            }
        }
        return true
    }

    private fun initializeGoogleAPIClient() {
        mGoogleApiClient = GoogleApiClient.Builder(RootActivity.rootActivity).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

        mLocationManager = RootActivity.rootActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE)
        if (!hasPermissions(context, PERMISSIONS)) {
            requestPermissions(PERMISSIONS, DashboardFragment.MY_PERMISSION_REQUEST_READ_LOCATION)
        }
    }

    private fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    override fun onClick(v: View) {

    }

    private fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(RootActivity.rootActivity)
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
                .setNegativeButton("No") { dialog, id ->

                    dialog.cancel()
                }
        val alert = builder.create()
        alert.show()
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.color_primary_orange))
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.color_primary_orange))
    }

    // GoogleAPI functions
    override fun onStart() {
        super.onStart()

        if (SharedPreferenceManager.getFeaturePreference(RootActivity.rootActivity) != null) {
            val feature = Integer.parseInt(SharedPreferenceManager.getFeaturePreference(RootActivity.rootActivity))
            if (feature == 14) {
                val locationManager = RootActivity.rootActivity.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
                if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) run { buildAlertMessageNoGps() } else {

                    if (mGoogleApiClient != null) {
                        mGoogleApiClient.connect()
                    }
                }
            }

        } else {
            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect()
            }
        }
    }


    override fun onStop() {
        super.onStop()
        if (mGoogleApiClient.isConnected) {
            mGoogleApiClient.disconnect()
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        mGoogleApiClient.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }

    override fun onLocationChanged(location: Location) {
    }

    override fun onConnected(p0: Bundle?) {
        val PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE)
        if (!hasPermissions(context, PERMISSIONS)) {
            requestPermissions(PERMISSIONS, DashboardFragment.MY_PERMISSION_REQUEST_READ_LOCATION)
        } else {
            //  getCurrentLocation()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == DashboardFragment.REQUEST_CHECK_SETTINGS) {
//            if (resultCode == Activity.RESULT_OK) {
//                locationUpdateState = true
//                startLocationUpdates()
//            }
//        }

        if (requestCode == DashboardFragment.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(context, data)
                var addressText = place.name.toString()
                addressText += "\n" + place.address.toString()

                placeMarkerOnMap(place.latLng)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        setActionbarTitle(false,false,R.string.hirePedal)
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

    private fun loadPlacePicker() {
        val builder = PlacePicker.IntentBuilder()

        try {
            startActivityForResult(builder.build(activity), DashboardFragment.PLACE_PICKER_REQUEST)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }


}

