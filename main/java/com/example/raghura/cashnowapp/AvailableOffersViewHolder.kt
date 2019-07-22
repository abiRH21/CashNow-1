package com.example.raghura.cashnowapp

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.row_view_offer.view.*

class AvailableOffersViewHolder (itemView: View, adapter: AvailableOffersListAdapter) : RecyclerView.ViewHolder(itemView) {
    private val amountTextView = itemView.offer_amount_text_view as TextView
    private val distanceTextView = itemView.offer_distance_text_view as TextView
    private val nameTextView = itemView.offer_name_text_view as TextView
    private val timeTextView = itemView.offer_time_text_view as TextView

    init {
        itemView.setOnClickListener {
            adapter.selectOfferAt(adapterPosition)
        }
    }

    fun bind(offer: Offer) {
        amountTextView.text = offer.amount
        distanceTextView.text = offer.distance
        nameTextView.text = offer.name
        timeTextView.text = "25 mins ago"

    }
}