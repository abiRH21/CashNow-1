package com.example.raghura.cashnowapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Offer (
    val amount: String = "",
    val distance: String = "",
    val name: String = ""
) : Parcelable