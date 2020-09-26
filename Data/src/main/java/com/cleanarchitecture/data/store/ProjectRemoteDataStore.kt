package com.cleanarchitecture.data.store

import com.cleanarchitecture.data.model.ProjectEntity
import com.cleanarchitecture.data.repository.ProjectsDataStore
import com.cleanarchitecture.data.repository.ProjectsRemote
import io.reactivex.Completable
import io.reactivex.Observable
import java.lang.UnsupportedOperationException
import javax.inject.Inject

class ProjectRemoteDataStore @Inject constructor(private val projectsRemote: ProjectsRemote) :
    ProjectsDataStore {
    override fun getProjects(): Observable<List<ProjectEntity>> {
        return projectsRemote.getProjects()
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        throw UnsupportedOperationException("getBookmarkedProjects isn't supported here ...")
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        throw UnsupportedOperationException("saveProjects isn't supported here ...")
    }

    override fun clearProjects(): Completable {
        throw UnsupportedOperationException("clearProjects isn't supported here ...")
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("setProjectAsBookmarked isn't supported here ...")
    }

    override fun setProjectAsUnBookmarked(projectId: String): Completable {
        throw UnsupportedOperationException("setProjectAsUnBookmarked isn't supported here ...")
    }
}