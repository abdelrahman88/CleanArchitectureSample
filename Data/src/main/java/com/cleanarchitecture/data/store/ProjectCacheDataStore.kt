package com.cleanarchitecture.data.store

import com.cleanarchitecture.data.model.ProjectEntity
import com.cleanarchitecture.data.repository.ProjectsCache
import com.cleanarchitecture.data.repository.ProjectsDataStore
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ProjectCacheDataStore @Inject constructor(private  val projectsCache: ProjectsCache) :
    ProjectsDataStore {
    override fun getProjects(): Observable<List<ProjectEntity>> {
        return projectsCache.getProjects()
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        return projectsCache.getBookmarkedProjects()
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        return projectsCache.saveProjects(projects)
    }

    override fun clearProjects(): Completable {
        return projectsCache.clearProjects()
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        return projectsCache.setProjectAsBookmarked(projectId)
    }

    override fun setProjectAsUnBookmarked(projectId: String): Completable {
        return projectsCache.setProjectAsUnBookmarked(projectId)
    }
}