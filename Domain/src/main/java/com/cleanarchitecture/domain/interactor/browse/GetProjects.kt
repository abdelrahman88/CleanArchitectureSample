package com.cleanarchitecture.domain.interactor.browse

import com.cleanarchitecture.domain.executor.PostExecutionThread
import com.cleanarchitecture.domain.interactor.ObservableUseCase
import com.cleanarchitecture.domain.model.Project
import com.cleanarchitecture.domain.repository.ProjectsRepository
import io.reactivex.Observable
import javax.inject.Inject

open class GetProjects @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Project> , Nothing?>(postExecutionThread) {

    override fun buildObservableUseCase(params: Nothing?): Observable<List<Project>> {
       return projectsRepository.getProjects()
    }
}