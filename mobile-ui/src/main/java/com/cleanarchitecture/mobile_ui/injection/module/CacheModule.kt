package com.cleanarchitecture.mobile_ui.injection.module

import android.app.Application
import com.cleanarchitecture.cache.ProjectsCacheImpl
import com.cleanarchitecture.cache.db.ProjectsDatabase
import com.cleanarchitecture.data.repository.ProjectsCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDataBase(application: Application): ProjectsDatabase {
            return ProjectsDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindProjectsCache(projectsCache: ProjectsCacheImpl): ProjectsCache
}