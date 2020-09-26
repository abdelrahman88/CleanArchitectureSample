package com.cleanarchitecture.remote

import com.cleanarchitecture.data.model.ProjectEntity
import com.cleanarchitecture.data.repository.ProjectsRemote
import com.cleanarchitecture.remote.mapper.ProjectResponseModelMapper
import com.cleanarchitecture.remote.service.GithubTrendingService
import io.reactivex.Observable
import javax.inject.Inject

class ProjectsRemoteImpl @Inject constructor(
    private val service: GithubTrendingService,
    private val mapper: ProjectResponseModelMapper
)  : ProjectsRemote {
    override fun getProjects(): Observable<List<ProjectEntity>> {
        return service.searchRepositories("language:kotlin", "stars", "desc")
            .map {
                it.items.map {
                    mapper.mapFromModel(it)
                }
            }
    }
}