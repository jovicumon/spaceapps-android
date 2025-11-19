package com.jovicumon.spaceapps.data.remote

import com.google.gson.annotations.SerializedName

// Modelo tal y como viene de la API de SpaceX v4/rockets.
// Me quedo con los campos que usaré en la UI (lista + detalle).
data class RocketDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("description")
    val description: String?,
    @SerializedName("first_flight")
    val firstFlight: String?,
    @SerializedName("success_rate_pct")
    val successRatePct: Int?,
    @SerializedName("wikipedia")
    val wikipedia: String?
)
