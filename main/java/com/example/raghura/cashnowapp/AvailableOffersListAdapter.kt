package com.example.raghura.cashnowapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

class AvailableOffersListAdapter (var context: Context?, var listener: AvailableOffersListFragment.OnOfferSelectedListener?) : RecyclerView.Adapter<AvailableOffersViewHolder>() {
    var offers : ArrayList<Offer>
    init {
        offers = ArrayList<Offer>()
        offers.add(Offer("1000 Japanese Yen","0.6","by Samuel"))
        offers.add(Offer("2000 Indian Rupees","0.2","by Eric"))


        Log.d("OOO", offers.toString())
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailableOffersViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_view_offer, parent, false)
        return AvailableOffersViewHolder(view, this)
    }
    override fun onBindViewHolder(holder: AvailableOffersViewHolder, position: Int) {
        holder.bind(offers[position])
    }
    override fun getItemCount() = offers.size
    fun selectOfferAt(adapterPosition: Int) {

        val offer = offers[adapterPosition]
        //  Toast.makeText(context, "Selected ${team.name}", Toast.LENGTH_LONG).show()
        listener?.onOfferSelected(offer)

    }
}