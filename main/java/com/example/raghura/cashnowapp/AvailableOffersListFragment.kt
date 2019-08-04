package com.example.raghura.cashnowapp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_offer_detail.*
import kotlinx.android.synthetic.main.fragment_offer_detail.view.*

private const val ARG_UID = "UID"
class AvailableOffersListFragment : Fragment() {
    private var uid: String? = null
    private var offerList : ArrayList<Offer>? = null
    private var listener: OnOfferSelectedListener? = null
    lateinit var adapter : AvailableOffersListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(R.layout.fragment_offers_list, container, false) as RecyclerView
        adapter = AvailableOffersListAdapter(context, listener, "")
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        (activity as MainActivity).fab.setOnClickListener{



            adapter.add(Offer ("100","0.2","Bob",uid!!))

        }

        return recyclerView


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uid = it.getString(ARG_UID)

        }
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
        fun onOfferSelectedAvailableOffers (offer : Offer)
    }
    companion object {
        @JvmStatic
        fun newInstance(uid: String , filter : Boolean) =
                AvailableOffersListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_UID, uid)

                    }
                }
    }

}