package com.example.raghura.cashnowapp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AvailableOffersListFragment : Fragment() {
    private var offerList : ArrayList<Offer>? = null
    private var listener: OnOfferSelectedListener? = null
    lateinit var adapter : AvailableOffersListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(R.layout.fragment_offers_list, container, false) as RecyclerView
        adapter = AvailableOffersListAdapter(context, listener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        return recyclerView


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnOfferSelectedListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnDocSelectedListener")
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnOfferSelectedListener {
        fun onOfferSelected (offer : Offer)
    }


}