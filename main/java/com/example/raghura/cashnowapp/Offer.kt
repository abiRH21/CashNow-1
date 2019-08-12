package com.example.raghura.cashnowapp

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Offer (
        var userAmount: String = "",
        var userCurrency : String ="",
        var desiredAmount: String ="",
        var desiredCurrency: String = "",
        var distance: String = "",
        var name: String = "",
        var creatorUID: String = "",
        var receiverUID:String ="",
        var accepted: String = "",
        var longitude: String = "",
        var latitude: String = "",
        var created: Date = Date(System.currentTimeMillis())
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