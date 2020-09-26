package com.cleanarchitecture.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cleanarchitecture.cache.dao.CachedProjectDao
import com.cleanarchitecture.cache.dao.ConfigDao
import com.cleanarchitecture.cache.db.ProjectConstants.DB_NAME
import com.cleanarchitecture.cache.model.CachedProject
import com.cleanarchitecture.cache.model.Config
import javax.inject.Inject

@Database(entities = arrayOf(CachedProject::class , Config::class), version = 1)
abstract class ProjectsDatabase @Inject constructor() : RoomDatabase() {


    abstract fun cachedProjectDao():CachedProjectDao
    abstract fun configDao():ConfigDao

    companion object {
        private var INSTANCE: ProjectsDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): ProjectsDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext
                            , ProjectsDatabase::class.java, DB_NAME
                        ).build()
                    }
                    return INSTANCE as ProjectsDatabase
                }
            }
            return INSTANCE as ProjectsDatabase
        }
    }
}