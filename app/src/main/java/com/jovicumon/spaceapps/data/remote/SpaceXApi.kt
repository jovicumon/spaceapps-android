package com.jovicumon.spaceapps.data.remote

import retrofit2.http.GET

interface SpaceXApi {

    // Endpoint de SpaceX v4 que devuelve todos los cohetes
    @GET("v4/rockets")
    suspend fun getRockets(): List<RocketDto>
}
