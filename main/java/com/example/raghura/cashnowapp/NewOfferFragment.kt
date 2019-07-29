package com.example.raghura.cashnowapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.fragment_new_offer.*
import kotlinx.android.synthetic.main.fragment_new_offer.view.*

class NewOfferFragment : Fragment() {
    var currencies = arrayOf("Rupees","Dollars", "Rubles")
    var spinner: Spinner? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_new_offer, container, false)
//        spinner = view.new_offer_currency
//        val aa = ArrayAdapter(container!!.context, R.layout.fragment_new_offer, currencies)
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//       new_offer_currency.adapter = aa
        return inflater.inflate(R.layout.fragment_new_offer, container, false)
    }

}