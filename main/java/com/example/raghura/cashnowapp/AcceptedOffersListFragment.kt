package com.example.raghura.cashnowapp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
private const val ARG_UID = "UID"
class AcceptedOffersListFragment : Fragment() {
    private var uid: String? = null
    private var offerList : ArrayList<Offer>? = null
    private var listener: OnOfferSelectedListenerAccepted? = null
    lateinit var adapter : AcceptedOffersListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(R.layout.fragment_offers_list, container, false) as RecyclerView
        adapter = AcceptedOffersListAdapter(context, listener, uid!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        return recyclerView


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uid = it.getString(ARG_UID)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is  OnOfferSelectedListenerAccepted) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnDocSelectedListener")
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnOfferSelectedListenerAccepted {
        fun onOfferSelected (offer : Offer)
    }
    companion object {
        @JvmStatic
        fun newInstance(uid: String , filter : Boolean) =
                AcceptedOffersListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_UID, uid)
                    }
                }
    }
}