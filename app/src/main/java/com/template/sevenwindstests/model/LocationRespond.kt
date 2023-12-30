package com.template.sevenwindstests.model

import java.math.BigDecimal

data class LocationRespond(
    val id: Int,
    val name: String,
    val point: Point
)

data class Point(
    val latitude: BigDecimal,
    val longitude: BigDecimal
)
