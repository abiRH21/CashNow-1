package com.example.raghura.cashnowapp

import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.row_view_offer.view.*
import java.time.Duration
import java.util.*

class MyOffersViewHolder (itemView: View, adapter: MyOffersListAdapter) : RecyclerView.ViewHolder(itemView)  {
    private val amountTextView = itemView.offer_amount_text_view as TextView
    private val distanceTextView = itemView.offer_distance_text_view as TextView
    private val nameTextView  = itemView.offer_name_text_view as TextView
    private val timeTextView = itemView.offer_time_text_view as TextView
    init {
        itemView.setOnClickListener{
            adapter.selectOfferAt(adapterPosition)
        }
        itemView.setOnLongClickListener{
            adapter.LPselectOffer(adapterPosition)
            true
        }
    }
    private fun getDuration(d1: Date, d2: Date): String {
        var diff = Duration.between(d1.toInstant(), d2.toInstant())


        val days = diff.toDays()
        diff = diff.minusDays(days)
        val hours = diff.toHours()
        diff = diff.minusHours(hours)
        val minutes = diff.toMinutes()
        diff = diff.minusMinutes(minutes)
        val seconds = diff.toMillis()

        val formattedDiff = StringBuilder()
        if (days != 0L) {
            if (days == 1L) {
                formattedDiff.append( "$days Day ")

            } else {
                formattedDiff.append("$days Days ")
            }
        }
        if (hours != 0L) {
            if (hours == 1L) {
                formattedDiff.append("$hours hour ")
            } else {
                formattedDiff.append("$hours hours ")
            }
        }
        if (minutes != 0L) {
            if (minutes == 1L) {
                formattedDiff.append("$minutes minute ")
            } else {
                formattedDiff.append("$minutes minutes ")
            }
        }



        return formattedDiff.toString()
    }















    fun bind(offer: Offer) {


        // Location.distanceBetween(37.421998333333335,-122.08400000000002, 37.421998333333335,-122.08400000000002, result)
        //   Log.d("OXOXO","${result[0].toInt().toString()} is the distance ")
        var diff : String  = getDuration(offer.created , Date(System.currentTimeMillis()))
        amountTextView.text = "${offer.userAmount} ${offer.userCurrency} for ${offer.desiredAmount} ${offer.desiredCurrency}"
        if (offer.accepted.equals("T")) {

            distanceTextView.text = "Status: Accepted"
        }
        else {
            distanceTextView.text = "Status: Not Accepted "
        }
        nameTextView.text = "by ${offer.name}"
        if (diff.equals("")){
            diff = "0 minutes ago"
            timeTextView.text = "$diff"
        } else {
            timeTextView.text = "$diff ago"
        }

    }
}