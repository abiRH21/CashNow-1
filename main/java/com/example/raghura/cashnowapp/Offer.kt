package com.example.raghura.cashnowapp

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Offer (
        val userAmount: String = "",
        val userCurrency : String ="",
        val desiredAmount: String ="",
        val desiredCurrency: String = "",
        val distance: String = "",
        val name: String = "",
        val creatorUID: String = "",
        val receiverUID:String ="",
        val accepted: String = "",
        val longitude: String = "",
        val latitude: String = ""
) : Parcelable
{
    @get:Exclude
    var id =""
    @ServerTimestamp
    var lastTouched: Timestamp? = null
    companion object {
        const val LAST_TOUCHED_KEY = "lastTouched"
        fun fromSnapshot(snapshot: DocumentSnapshot) : Offer {
            val mq = snapshot.toObject(Offer::class.java)!!
            mq.id = snapshot.id
            return mq
        }
    }
}