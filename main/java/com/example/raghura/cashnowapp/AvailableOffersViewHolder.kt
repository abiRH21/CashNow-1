package com.example.raghura.cashnowapp

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.row_view_offer.view.*

class AvailableOffersViewHolder (itemView: View, adapter: AvailableOffersListAdapter, context : Context?) : RecyclerView.ViewHolder(itemView) {
    var userOffer : Offer? = null
    lateinit var userContext: Context
    private val amountTextView = itemView.offer_amount_text_view as TextView
    private val distanceTextView = itemView.offer_distance_text_view as TextView
    private val nameTextView = itemView.offer_name_text_view as TextView
    private val timeTextView = itemView.offer_time_text_view as TextView
    private var locationManager : LocationManager? = null
    var userLong : Double = 0.0
    var userLat : Double = 0.0
    val result : FloatArray = FloatArray(10)
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            Log.d("OXOXO","from View Holder ${location.longitude} ${location.latitude}")
            //latitude.setText("" + location.longitude + ":" + location.latitude);
            userLong = location.longitude
            userLat = location.latitude
            Location.distanceBetween(userLat,userLong, userOffer!!.latitude.toDouble(),200.0, result)
            var number : Float =  1609.344F
            result[0] = result[0]/ number
            distanceTextView.text = "Distance: ${result[0].toString()} miles"
            // result[0] is the distance i

            Log.d("OXOXO", "${result[0]} is the distance ")
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
    init {
        itemView.setOnClickListener {
            adapter.selectOfferAt(adapterPosition)
        }
        userContext = context!!


    }

    fun bind(offer: Offer) {

        userOffer = offer
        locationManager = userContext.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager?
        try {
            // Request location updates
            locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
            //Log.d("OXOXO","${userLat}")
        } catch(ex: SecurityException) {
            Log.d("myTag", "Security Exception, no location available");
        }

       // Location.distanceBetween(37.421998333333335,-122.08400000000002, 37.421998333333335,-122.08400000000002, result)
     //   Log.d("OXOXO","${result[0].toInt().toString()} is the distance ")
        amountTextView.text = "${offer.userAmount} ${offer.userCurrency} for ${offer.desiredAmount} ${offer.desiredCurrency}"
        distanceTextView.text = "Calculating distance..."
        nameTextView.text = "by ${offer.name}"
        timeTextView.text = "Posted 25 mins ago"

    }
}