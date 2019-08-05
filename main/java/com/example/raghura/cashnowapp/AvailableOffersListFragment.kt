package com.example.raghura.cashnowapp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.example.raghura.cashnowapp.R.id.recycler_view
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add.*
import kotlinx.android.synthetic.main.dialog_add.view.*
import kotlinx.android.synthetic.main.dialog_filter.view.*
import kotlinx.android.synthetic.main.fragment_new_offer.*
import kotlinx.android.synthetic.main.fragment_offer_detail.*
import kotlinx.android.synthetic.main.fragment_offer_detail.view.*

private const val ARG_UID = "UID"
private const val ARG_USER = "USER"
class AvailableOffersListFragment : Fragment() {
    private var user: FirebaseUser? = null
    private var uid: String? = null
    private var offerList : ArrayList<Offer>? = null
    private var listener: OnOfferSelectedListener? = null
    lateinit var adapter : AvailableOffersListAdapter

    var currencies = arrayOf("Rupees","Dollars", "Rubles")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val recyclerView = inflater.inflate(R.layout.fragment_offers_list, container, false) as RecyclerView
        adapter = AvailableOffersListAdapter(context, listener, "")
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        (activity as MainActivity).fab.setOnClickListener{


            addOption(inflater, container)
//            adapter.add(Offer ("100","0.2", user!!.displayName!!,uid!!))
//            Log.d("XXX", "${user!!.displayName}" )

        }

        return recyclerView


    }


    private fun addOption(inflater: LayoutInflater, container: ViewGroup?) {
        //val view = inflater.inflate(R.layout.dialog_add, container, false)
        val builder = AlertDialog.Builder(container!!.context)
        val view = LayoutInflater.from(container!!.context).inflate(R.layout.dialog_add,null,false)
        val userCurrencyspinner = view.dialog_add_user_currency_spinner
        val desiredCurrencyspinner = view.dialog_add_desired_currency_spinner
        userCurrencyspinner.adapter = ArrayAdapter(activity, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.currencies)) as SpinnerAdapter?
        desiredCurrencyspinner.adapter = ArrayAdapter(activity, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.currencies)) as SpinnerAdapter?
        builder.setView(view)
        builder.setTitle("New Offer")
        builder.setPositiveButton(android.R.string.ok) { _,_ ->

            val userAmount = view.dialog_add_user_amount.text.toString()
            val desiredAmount = view.dialog_add_desired_amount.text.toString()
            val userCurrency = view.dialog_add_user_currency_spinner.getSelectedItem().toString()
            val desiredCurrency = view.dialog_add_desired_currency_spinner.getSelectedItem().toString()

            adapter.add(Offer(userAmount,userCurrency,desiredAmount,desiredCurrency,"0.2 miles", user!!.displayName!!, uid!!))

        }
        builder.setNegativeButton(android.R.string.cancel , null)
        builder.create().show()

    }









    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uid = it.getString(ARG_UID)
            user = it.getParcelable(ARG_USER)
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
        fun newInstance(uid: String , filter : Boolean, user: FirebaseUser) =
                AvailableOffersListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_UID, uid)
                        putParcelable(ARG_USER, user)

                    }
                }
    }

}