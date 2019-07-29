package com.example.raghura.cashnowapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast

class MyOffersListAdapter (var context: Context?, var listener: MyOffersListFragment.OnOfferSelectedListener?) : RecyclerView.Adapter<MyOffersViewHolder>() {
     var offers : ArrayList<Offer>
    init {
        offers = ArrayList<Offer>()
        offers.add(Offer("100","0.3","by Adam "))
        offers.add(Offer("500","0.4","by John"))


        Log.d("OOO", offers.toString())
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOffersViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_view_offer, parent, false)
        return MyOffersViewHolder(view, this)
    }
    override fun onBindViewHolder(holder: MyOffersViewHolder, position: Int) {
        holder.bind(offers[position])
    }
    override fun getItemCount() = offers.size
    fun selectOfferAt(adapterPosition: Int) {

        val offer = offers[adapterPosition]
      //  Toast.makeText(context, "Selected ${team.name}", Toast.LENGTH_LONG).show()
        listener?.onOfferSelected(offer)

    }
}