package com.jovicumon.spaceapps.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpaceXApiClient {

    private const val BASE_URL = "https://api.spacexdata.com/"

    // Uso un singleton para reutilizar la misma instancia de Retrofit en toda la app.
    val api: SpaceXApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpaceXApi::class.java)
    }
}
