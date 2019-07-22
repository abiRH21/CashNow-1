package com.example.raghura.cashnowapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_offer_detail.view.*

private const val ARG_DOC = "OFFER"
class OfferDetailFragment : Fragment() {
    private var offer: Offer? = null
    companion object {

        @JvmStatic
        fun newInstance(offer: Offer) =
                OfferDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_DOC, offer)
                    }
                }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            offer = it.getParcelable(ARG_DOC)

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
        view.detail_amount_text_view.text = offer?.amount
        view.detail_distance_text_view.text = offer?.distance
        view.detail_location_text_view.text = "Dubai Internaional Airport"
        view.detail_name_text_view.text = offer?.name
        return view
    }

}