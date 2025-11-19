package com.jovicumon.spaceapps.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Tabla de Room donde guardaré los cohetes de SpaceX en local.
@Entity(tableName = "rockets")
data class RocketEntity(
    @PrimaryKey val id: String,
    val name: String,
    val active: Boolean,
    val description: String?,
    val firstFlight: String?,
    val successRatePct: Int?,
    val wikipedia: String?
)
