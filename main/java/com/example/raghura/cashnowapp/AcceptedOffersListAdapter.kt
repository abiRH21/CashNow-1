package com.example.raghura.cashnowapp

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.*

class AcceptedOffersListAdapter (var context: Context?, var listener: AcceptedOffersListFragment.OnOfferSelectedListenerAccepted?, uid : String ) : RecyclerView.Adapter<AcceptedOffersViewHolder>() {
    val offers = ArrayList<Offer>()
    private var quoteRef = FirebaseFirestore
            .getInstance()
            .collection("acceptedOffers")
    lateinit var Cuid : String
    lateinit var thisContext : Context
    init {
        var checkSize : Boolean = false

        quoteRef
                .orderBy(Offer.LAST_TOUCHED_KEY, Query.Direction.ASCENDING).whereEqualTo("creatorUID",uid)//.whereEqualTo("accepted","T").whereEqualTo("creatorUID",uid)

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
                                notifyItemInserted(0)

                            }
                            DocumentChange.Type.REMOVED -> {
                                val pos = offers.indexOfFirst { offer.id == it.id }
                                offers.removeAt(pos)
                                notifyItemRemoved(pos)
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
                                notifyItemChanged(pos)
                            }
                        }
                    }
                  if (offers.size >0){
                      checkSize = true
                  }

                }
//            if (checkSize == true ) {
//                quoteRef = FirebaseFirestore
//                        .getInstance()
//                        .collection("offers")
//
//                quoteRef
//                        .orderBy(Offer.LAST_TOUCHED_KEY, Query.Direction.ASCENDING).whereEqualTo("accepted","T").whereEqualTo("receiverUID",uid)
//
//                .addSnapshotListener { snapshot: QuerySnapshot?, exception: FirebaseFirestoreException? ->
//                    if (exception != null) {
//                        //Log.e(Constants.TAG, "Listen error: $exception")
//                        return@addSnapshotListener
//                    }
//                    for (docChange in snapshot!!.documentChanges) {
//                        val offer = Offer.fromSnapshot(docChange.document)
//                        when (docChange.type) {
//                            DocumentChange.Type.ADDED -> {
//
//                                offers.add(0, offer)
//                                notifyItemInserted(0)
//
//                            }
//                            DocumentChange.Type.REMOVED -> {
//                                val pos = offers.indexOfFirst { offer.id == it.id }
//                                offers.removeAt(pos)
//                                notifyItemRemoved(pos)
////                                for ((pos,mq) in movieQuotes.withIndex()) {
////                                if (mq.id == movieQuote.id) {
////                                    movieQuotes.removeAt(pos)
////                                    notifyItemRemoved(pos)
////                                    break
////                                }
////                                }
//                            }
//                            DocumentChange.Type.MODIFIED -> {
//                                val pos = offers.indexOfFirst { offer.id == it.id }
//                                offers[pos] = offer
//                                notifyItemChanged(pos)
//                            }
//                        }
//                    }
//
//                }
//
//
//
//
//
//
//            }

    }
    override fun getItemCount() = offers.size
    override fun onBindViewHolder(holder: AcceptedOffersViewHolder, position: Int) {

        holder.bind(offers[position])
    }
    lateinit var Gview : View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcceptedOffersViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_view_offer, parent, false)

        Gview = view
        return AcceptedOffersViewHolder(view, this)


    }
    fun selectOfferAt(adapterPosition: Int) {

        val offer = offers!![adapterPosition]
        //Toast.makeText(context, "Selected ${pic.caption}", Toast.LENGTH_LONG).show()
        listener?.onOfferSelected(offer)

    }
    fun add (offer: Offer) {
        quoteRef.add(offer)
//       pics!!.add(pic)
//        notifyItemInserted(0)

    }
    fun remove(position: Int) {
        val offer = offers!![position]

        quoteRef.document(offers[position].id).delete()


    }
}