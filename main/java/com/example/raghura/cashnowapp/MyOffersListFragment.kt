package com.example.raghura.cashnowapp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
private const val ARG_UID = "UID"
class MyOffersListFragment : Fragment() {
    private var uid: String? = null
   private var offerList : ArrayList<Offer>? = null
    private var listener: OnOfferSelectedListener? = null
    lateinit var adapter : MyOffersListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(R.layout.fragment_offers_list, container, false) as RecyclerView
        adapter = MyOffersListAdapter(context, listener , uid!!)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.RIGHT) {
                    adapter.remove(position)

                }
            }
        }
        val helper = ItemTouchHelper(simpleItemTouchCallback)
        helper.attachToRecyclerView(recyclerView)




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

    companion object {
        @JvmStatic
        fun newInstance(uid: String , filter : Boolean) =
                MyOffersListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_UID, uid)
                    }
                }
    }
}