package com.example.raghura.cashnowapp

import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_offer_detail.view.*

private const val ARG_DOC = "OFFER"
private const val ARG_TYPE = "TYPE"

class OfferDetailFragment : Fragment() {
    private var offer: Offer? = null
    private var type: Int? = 0
    companion object {

        @JvmStatic
        fun newInstance(offer: Offer, type: Int) =
                OfferDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_DOC, offer)
                        putInt(ARG_TYPE, type)
                    }
                }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            offer = it.getParcelable(ARG_DOC)
            type = it.getInt(ARG_TYPE)

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_offer_detail, container, false)
//        Picasso.get().load(pic?.url).into(view.fragment_pic_detail_url)
//        //find way to make fab invisible
//        (activity as MainActivity).fab.hide()
//        view.fragment_pic_detail_caption.text = pic?.caption
        view.detail_amount_text_view.text = "Offer is ${offer?.amount} for 25 U.S Dollars"
        view.detail_distance_text_view.text = "Distance: ${offer?.distance} miles"
        view.detail_location_text_view.text = "Meet up Location: Gate 21, Terminal 3, Dubai International "
        view.detail_name_text_view.text = offer?.name
        if (type == 1) {
            view.detail_button.visibility = View.INVISIBLE
            view.detail_location_text_view.text = "Meet up Location: Gate 21, Terminal 3, Dubai  \n ${offer?.name} phone number : +1 999-999-9999"
        }
        return view
    }

}