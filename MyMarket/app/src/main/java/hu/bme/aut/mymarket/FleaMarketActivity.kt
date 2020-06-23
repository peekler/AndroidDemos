package hu.bme.aut.mymarket

import android.content.ActivityNotFoundException
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_flea_market.*
import java.util.*


class FleaMarketActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flea_market)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as ScrollSupportMapFragment
        mapFragment.getMapAsync(this)

        btnPublish.setOnClickListener {
            publish()
        }
        btnNavigate.setOnClickListener {
            navigateMarket()
        }
        btnStreetView.setOnClickListener {
            showMarketStreet()
        }
    }


    fun revealDetails() {
        val x = cardDetails.left
        val y = cardDetails.top

        val startRadius = 0
        val endRadius = Math.hypot(cardDetails.getWidth().toDouble(),
            cardDetails.getHeight().toDouble()).toInt()

        val anim = ViewAnimationUtils.createCircularReveal(
            cardDetails,
            x,
            y,
            startRadius.toFloat(),
            endRadius.toFloat()
        )

        cardDetails.setVisibility(View.VISIBLE)
        anim.start()
    }

    var newMarker: Marker? = null

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as ScrollSupportMapFragment
        mapFragment.setListener(object: ScrollSupportMapFragment.OnTouchListener{
            override fun onTouch() {
                scrollMap.requestDisallowInterceptTouchEvent(true)
            }
        })

        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.setOnMapClickListener {
            if (newMarker == null) {
                mMap.clear()
                newMarker = mMap.addMarker(
                    MarkerOptions()
                        .position(it)
                        .title("My marker")
                        .snippet("long text of the marker")
                )
                newMarker?.isDraggable = true
            } else {
                newMarker?.position = it
            }

            updateAddress()

            mMap.animateCamera(CameraUpdateFactory.newLatLng(it))
        }
    }


    private fun updateAddress() {
        var lat = newMarker!!.position.latitude
        var lng = newMarker!!.position.longitude
        Thread {
            try {
                val gc = Geocoder(this, Locale.getDefault())
                var addrs: List<Address> =
                    gc.getFromLocation(lat, lng, 3)
                val addr =
                    "${addrs[0].getAddressLine(0)}"

                runOnUiThread {
                    etDetails.setText(addr)
                    revealDetails()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(
                        this,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }.start()
    }

    private fun publish() {
        val intentSend = Intent(Intent.ACTION_SEND)
        intentSend.type = "text/plain"
        intentSend.putExtra(Intent.EXTRA_TEXT, "https://www.facebook.com/bolhapiacom/")

        intentSend.setPackage("com.facebook.katana")

        try {
            startActivity(intentSend)
        } catch (e: ActivityNotFoundException){
            e.printStackTrace()
        }
    }

    private fun navigateMarket() {
        val wazeUri = "waze://?q=${etDetails.text}&navigate=yes"

        val intentTest = Intent(Intent.ACTION_VIEW)
        intentTest.data = Uri.parse(wazeUri)
        startActivity(intentTest)
    }

    private fun showMarketStreet(){
        var lat = newMarker!!.position.latitude
        var lng = newMarker!!.position.longitude
        val gmmIntentUri = Uri.parse("google.streetview:cbll=$lat,$lng")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}