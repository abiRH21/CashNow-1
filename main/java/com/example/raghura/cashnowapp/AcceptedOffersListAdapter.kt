package com.example.raghura.cashnowapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

class AcceptedOffersListAdapter (var context: Context?, var listener: AcceptedOffersListFragment.OnOfferSelectedListenerAccepted?) : RecyclerView.Adapter<AcceptedOffersViewHolder>() {
    var offers : ArrayList<Offer>
    init {
        offers = ArrayList<Offer>()
        offers.add(Offer("10230","200","by Adam "))
        offers.add(Offer("50012","400","by John"))


        Log.d("OOO", offers.toString())
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcceptedOffersViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_view_offer, parent, false)
        return AcceptedOffersViewHolder(view, this)
    }
    override fun onBindViewHolder(holder: AcceptedOffersViewHolder, position: Int) {
        holder.bind(offers[position])
    }
    override fun getItemCount() = offers.size
    fun selectOfferAt(adapterPosition: Int) {

        val offer = offers[adapterPosition]
        //  Toast.makeText(context, "Selected ${team.name}", Toast.LENGTH_LONG).show()
        listener?.onOfferSelected(offer)

    }
}