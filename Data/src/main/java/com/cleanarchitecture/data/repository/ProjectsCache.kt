package com.cleanarchitecture.data.repository

import com.cleanarchitecture.data.model.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ProjectsCache {

    fun clearProjects() :Completable
    fun saveProjects(projects : List<ProjectEntity>) : Completable
    fun getProjects() : Observable<List<ProjectEntity>>
    fun getBookmarkedProjects() : Observable<List<ProjectEntity>>
    fun setProjectAsBookmarked(projectID : String) : Completable
    fun setProjectAsUnBookmarked(projectID: String) : Completable
    fun areProjectsCashed() : Single<Boolean>
    fun setLastCashTime(lastCache : Long) : Completable
    fun isProjectsCacheExpired() : Single<Boolean>

}