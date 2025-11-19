package com.jovicumon.spaceapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Tabla de Room donde guardaré los cohetes con todos los datos necesarios para el detalle.
@Entity(tableName = "rockets")
data class RocketEntity(
    @PrimaryKey val id: String,
    val name: String,
    val active: Boolean,
    val description: String?,
    val firstFlight: String?,
    val successRatePct: Int?,
    val wikipedia: String?,
    val country: String?,
    val stages: Int?,
    val costPerLaunch: Long?,
    val imageUrl: String? // solo guardo la primera imagen
)
