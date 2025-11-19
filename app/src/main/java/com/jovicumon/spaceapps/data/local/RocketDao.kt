package com.jovicumon.spaceapps.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RocketDao {

    @Query("SELECT * FROM rockets ORDER BY name")
    fun getAllRockets(): kotlinx.coroutines.flow.Flow<List<RocketEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rockets: List<RocketEntity>)

    @Query("DELETE FROM rockets")
    suspend fun clearAll()

    // 🔁 Lo usaré para la pantalla de detalle
    @Query("SELECT * FROM rockets WHERE id = :id LIMIT 1")
    suspend fun getRocketById(id: String): RocketEntity?
}
