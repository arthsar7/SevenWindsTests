package com.template.sevenwindstests.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoffeeItem(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val price: Int,
    var count: Int? = 0
) : Parcelable
