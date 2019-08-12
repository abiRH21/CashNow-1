package com.example.raghura.cashnowapp

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.dialog_add.view.*

class MyOffersListAdapter (var context: Context?, var listener: MyOffersListFragment.OnOfferSelectedListener? , uid : String ) : RecyclerView.Adapter<MyOffersViewHolder>() {
    val offers = ArrayList<Offer>()
    lateinit var currentContext: Context
    private val quoteRef = FirebaseFirestore
            .getInstance()
            .collection("offers")
    lateinit var Cuid : String
    lateinit var thisContext : Context
    init {
        currentContext = context!!
        Log.d("UUU", uid)
        quoteRef
                .orderBy(Offer.LAST_TOUCHED_KEY, Query.Direction.ASCENDING).whereEqualTo("creatorUID",uid)

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

                }
    }


    override fun getItemCount() = offers.size
    override fun onBindViewHolder(holder: MyOffersViewHolder, position: Int) {

        holder.bind(offers[position])
    }
    lateinit var Gview : View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOffersViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_view_offer, parent, false)

        Gview = view
        return MyOffersViewHolder(view, this)

    }
    fun selectOfferAt(adapterPosition: Int) {

        val offer = offers!![adapterPosition]
        //Toast.makeText(context, "Selected ${pic.caption}", Toast.LENGTH_LONG).show()
        listener?.onOfferSelected(offer)

    }

    fun showEditDialog (offer: Offer , pos: Int) {
        val builder=  AlertDialog.Builder(context!!)

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add, null ,false)
        val userCurrencyspinner = view.dialog_add_user_currency_spinner
        val desiredCurrencyspinner = view.dialog_add_desired_currency_spinner
        userCurrencyspinner.adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, currentContext.resources.getStringArray(R.array.currencies)) as SpinnerAdapter?
        desiredCurrencyspinner.adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, currentContext.resources.getStringArray(R.array.currencies)) as SpinnerAdapter?
        builder.setView(view)
        builder.setTitle("Edit Offer")
        view.dialog_add_user_amount.setText(offer.userAmount)
        view.dialog_add_desired_amount.setText(offer.desiredAmount)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            edit(pos, view.dialog_add_user_amount.text.toString() , view.dialog_add_user_currency_spinner.getSelectedItem().toString(), view.dialog_add_desired_amount.text.toString(), view.dialog_add_desired_currency_spinner.getSelectedItem().toString())
            Toast.makeText(context, "Successfully edited Offer", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton(android.R.string.cancel , null)
        builder.show()
    }


    fun LPselectOffer (adapterPosition: Int) {
        val offer = offers!![adapterPosition]
        if(offer.accepted.equals("F")) {

            showEditDialog(offer, adapterPosition)

        }
        else {
            Toast.makeText(context, "This offer cannot be edited as it has already been accepted", Toast.LENGTH_LONG).show()
        }

    }


    fun add (offer: Offer) {
        quoteRef.add(offer)
//       pics!!.add(pic)
//        notifyItemInserted(0)

    }
    fun remove(position: Int) {
        val offer = offers!![position]
        if(offer.accepted.equals("F")) {
            quoteRef.document(offers[position].id).delete()
            Toast.makeText(context, "Successfully deleted offer", Toast.LENGTH_LONG).show()
        }
        else {
            notifyItemChanged(position)
            Toast.makeText(context, "Cannot delete as offer has already been accepted", Toast.LENGTH_LONG).show()
        }


    }
    private fun edit( pos: Int , userAmount: String , userCurrency :String , desiredAmount: String , desiredCurrency : String) {
        offers[pos].userAmount = userAmount
        offers[pos].userCurrency = userCurrency
        offers[pos].desiredAmount = desiredAmount
        offers[pos].desiredCurrency = desiredCurrency
        quoteRef.document(offers[pos].id).set(offers[pos])

    }
}