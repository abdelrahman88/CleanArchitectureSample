package com.cleanarchitecture.data.store

import com.cleanarchitecture.data.repository.ProjectsDataStore
import com.cleanarchitecture.data.store.ProjectCacheDataStore
import com.cleanarchitecture.data.store.ProjectRemoteDataStore
import javax.inject.Inject

open class ProjectsDataStoreFactory @Inject constructor(
    private val projectCacheDataStore: ProjectCacheDataStore
    , private val projectRemoteDataStore: ProjectRemoteDataStore
)  {

    open fun getDataStore(projectsCached : Boolean , cacheExpired :Boolean) : ProjectsDataStore {
            return if(projectsCached && !cacheExpired){
                projectCacheDataStore
            }
        else{
                projectRemoteDataStore
            }
    }

    open fun getCacheDataStore() : ProjectsDataStore {
        return projectCacheDataStore
    }
}