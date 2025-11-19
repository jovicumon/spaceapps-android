package com.jovicumon.spaceapps.data.repository

import com.jovicumon.spaceapps.data.local.RocketDao
import com.jovicumon.spaceapps.data.local.RocketEntity
import com.jovicumon.spaceapps.data.remote.RocketDto
import com.jovicumon.spaceapps.data.remote.SpaceXApi
import kotlinx.coroutines.flow.first

class RocketRepository(
    private val api: SpaceXApi,
    private val rocketDao: RocketDao
) {

    // Descargo cohetes de la API y los guardo en Room.
    suspend fun refreshRockets() {
        val remoteRockets = api.getRockets()
        val entities = remoteRockets.map { it.toEntity() }

        rocketDao.clearAll()
        rocketDao.insertAll(entities)
    }

    // Devuelvo la lista de cohetes que haya ahora mismo en Room (una sola vez)
    suspend fun getRocketsOnce(): List<RocketEntity> {
        return rocketDao.getAllRockets().first()
    }

    // Lo usaremos para detalle si hiciera falta
    suspend fun getRocketById(rocketId: String): RocketEntity? {
        return rocketDao.getRocketById(rocketId)
    }

    // DTO -> Entity con todos los campos
    private fun RocketDto.toEntity(): RocketEntity {
        return RocketEntity(
            id = this.id,
            name = this.name,
            active = this.active,
            description = this.description,
            firstFlight = this.firstFlight,
            successRatePct = this.successRatePct,
            wikipedia = this.wikipedia,
            country = this.country,
            stages = this.stages,
            costPerLaunch = this.costPerLaunch,
            imageUrl = this.flickrImages?.firstOrNull() // cojo solo la primera imagen
        )
    }
}
