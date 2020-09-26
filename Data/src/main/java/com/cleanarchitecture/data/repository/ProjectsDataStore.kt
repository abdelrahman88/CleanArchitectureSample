package com.cleanarchitecture.data.repository

import com.cleanarchitecture.data.model.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface ProjectsDataStore {

    fun getProjects() : Observable<List<ProjectEntity>>
    fun getBookmarkedProjects() : Observable<List<ProjectEntity>>
    fun saveProjects(projects  : List<ProjectEntity>) : Completable
    fun clearProjects() : Completable
    fun setProjectAsBookmarked(projectId : String) : Completable
    fun setProjectAsUnBookmarked(projectId : String) : Completable
}