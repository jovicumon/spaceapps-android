package com.jovicumon.spaceapps.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RocketEntity::class],
    version = 3, // subo versión porque he cambiado columnas varias veces
    exportSchema = false
)
abstract class SpaceAppsDatabase : RoomDatabase() {

    abstract fun rocketDao(): RocketDao

    companion object {

        @Volatile
        private var INSTANCE: SpaceAppsDatabase? = null

        fun getInstance(context: Context): SpaceAppsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SpaceAppsDatabase::class.java,
                    "spaceapps_db"
                )
                    // borrar y recrear la BD en cada cambio de schema
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
