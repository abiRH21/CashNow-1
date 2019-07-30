package com.example.raghura.cashnowapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter
import com.firebase.ui.auth.data.model.Resource
import com.google.common.io.Resources
import kotlinx.android.synthetic.main.fragment_new_offer.*
import kotlinx.android.synthetic.main.fragment_new_offer.view.*

class NewOfferFragment : Fragment() , AdapterView.OnItemSelectedListener{
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var currencies = arrayOf("Rupees","Dollars", "Rubles")
  //  var spinner: Spinner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_new_offer, container, false)

        view.new_offer_currency_desired_spinner!!.setOnItemSelectedListener(this)
//        val aa = ArrayAdapter(activity, android.R.layout.simple_spinner_item, currencies)
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        view.new_offer_currency_desired_spinner.setAdapter(aa)

        return inflater.inflate(R.layout.fragment_new_offer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner = new_offer_currency_desired_spinner
        spinner.adapter = ArrayAdapter(activity, R.layout.support_simple_spinner_dropdown_item, resources.getStringArray(R.array.currencies)) as SpinnerAdapter?
/*set click listener*/
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                val num = when (spinner.selectedItem.toString()) {
////                    "H" -> editText.setText("1")
////                    "He" -> editText.setText("4")
////                    "C" -> editText.setText("12")
////                    "O" -> editText.setText("16")
////                    else -> editText.setText("")
//                }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//                /*Do something if nothing selected*/
//            }
//        }

    }
}