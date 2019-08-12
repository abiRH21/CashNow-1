package com.example.raghura.cashnowapp

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_offer_detail.view.*

private const val ARG_DOC = "OFFER"
private const val ARG_UID = "UID"
private const val ARG_TYPE = "TYPE"

class OfferDetailFragment : Fragment() {
    private val quoteRef = FirebaseFirestore
            .getInstance()
            .collection("offers")
    private val quoteRefAccepted = FirebaseFirestore
            .getInstance()
            .collection("acceptedOffers")
    private var offer: Offer? = null
    private var type: Int? = 0
    private var uid: String?= ""
    val offers = ArrayList<Offer>()
    val acceptedOffers = ArrayList<Offer>()
    init {
        quoteRef
                .orderBy(Offer.LAST_TOUCHED_KEY, Query.Direction.ASCENDING)//.whereEqualTo("uid",Cuid)

                .addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
                    if (exception != null) {
                        //Log.e(Constants.TAG, "Listen error: $exception")
                        return@addSnapshotListener
                    }
                    for (docChange in snapshot!!.documentChanges) {
                        val offer = Offer.fromSnapshot(docChange.document)
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {

                                offers.add(0, offer)

                            }
                            DocumentChange.Type.REMOVED -> {
                                val pos = offers.indexOfFirst { offer.id == it.id }
                                offers.removeAt(pos)
                               // notifyItemRemoved(pos)
//                                for ((pos,mq) in movieQuotes.withIndex()) {
//                                if (mq.id == movieQuote.id) {
//                                    movieQuotes.removeAt(pos)
//                                    notifyItemRemoved(pos)
//                                    break
//                                }
//                                }
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val pos = offers.indexOfFirst { offer.id == it.id }
                                offers[pos] = offer
                               // notifyItemChanged(pos)
                            }
                        }
                    }

                }



        // snapshot listener for accepted offers
        quoteRefAccepted
                .orderBy(Offer.LAST_TOUCHED_KEY, Query.Direction.ASCENDING)//.whereEqualTo("uid",Cuid)

                .addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
                    if (exception != null) {
                        //Log.e(Constants.TAG, "Listen error: $exception")
                        return@addSnapshotListener
                    }
                    for (docChange in snapshot!!.documentChanges) {
                        val offer = Offer.fromSnapshot(docChange.document)
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {

                                acceptedOffers.add(0, offer)

                            }
                            DocumentChange.Type.REMOVED -> {
                                val pos = acceptedOffers.indexOfFirst { offer.id == it.id }
                                acceptedOffers.removeAt(pos)
                                // notifyItemRemoved(pos)
//                                for ((pos,mq) in movieQuotes.withIndex()) {
//                                if (mq.id == movieQuote.id) {
//                                    movieQuotes.removeAt(pos)
//                                    notifyItemRemoved(pos)
//                                    break
//                                }
//                                }
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val pos = acceptedOffers.indexOfFirst { offer.id == it.id }
                                acceptedOffers[pos] = offer
                                // notifyItemChanged(pos)
                            }
                        }
                    }

                }








    }
    companion object {

        @JvmStatic
        fun newInstance(offer: Offer, type: Int, uid : String) =
                OfferDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_DOC, offer)
                        putInt(ARG_TYPE, type)
                        putString(ARG_UID ,uid)

                    }
                }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            offer = it.getParcelable(ARG_DOC)
            type = it.getInt(ARG_TYPE)
            uid = it.getString(ARG_UID)

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
        view.detail_amount_text_view.text = "Offer is ${offer?.userAmount} ${offer?.userCurrency} for ${offer?.desiredAmount} ${offer?.desiredCurrency}"
        //view.detail_distance_text_view.text = "Distance: ${offer?.distance} miles"
        view.detail_location_text_view.text = "Meet up Location: Gate 21, Terminal 3, Dubai International "
        view.detail_name_text_view.text = "Offer posted by ${offer?.name}"
        if (type == 1 || offer!!.creatorUID!!.equals(uid)) {
            view.detail_button.visibility = View.INVISIBLE
            view.detail_location_text_view.text = "Meet up Location: Gate 21, Terminal 3, Dubai  \n "
        }
                view.detail_button.setOnClickListener {
                 //   quoteRef.document(offers[position].id).set(offers[position])
                    Log.d("TTT", "${offer!!.creatorUID}")
                    var pos : String =""
                    var  cID : String =""
                    for (o in offers) {
                        if ((o.creatorUID == offer!!.creatorUID) && !o.accepted.equals("T"))
                        pos = o.id
                        cID = o.creatorUID
                    }
                    val newOffer: Offer = Offer(offer!!.userAmount, offer!!.userCurrency, offer!!.desiredAmount, offer!!.desiredCurrency, offer!!.distance, offer!!.name, offer!!.creatorUID, uid!!, "T", offer!!.longitude , offer!!.latitude)
                    quoteRef.document(pos).set(newOffer).addOnSuccessListener {
                        Log.d("CCC", offers.toString())
                    }
                  //  quoteRef.document(pos).delete()
                    val acceptedOffer1 : Offer = Offer(offer!!.userAmount, offer!!.userCurrency, offer!!.desiredAmount, offer!!.desiredCurrency, offer!!.distance, offer!!.name, offer!!.creatorUID, uid!!)
                    val acceptedOffer2 : Offer = Offer(offer!!.userAmount, offer!!.userCurrency, offer!!.desiredAmount, offer!!.desiredCurrency, offer!!.distance, offer!!.name, uid!!,offer!!.creatorUID)
                    quoteRefAccepted.add(acceptedOffer1)
                    quoteRefAccepted.add(acceptedOffer2)
                    Toast.makeText(context,"Successfully accepted offer!", Toast.LENGTH_LONG).show()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).fab.show()
    }

}