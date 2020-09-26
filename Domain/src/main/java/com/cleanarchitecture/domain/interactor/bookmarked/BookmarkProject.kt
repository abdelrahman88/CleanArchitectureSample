package com.cleanarchitecture.domain.interactor.bookmarked

import com.cleanarchitecture.domain.executor.PostExecutionThread
import com.cleanarchitecture.domain.interactor.CompletableUseCase
import com.cleanarchitecture.domain.repository.ProjectsRepository
import io.reactivex.Completable
import javax.inject.Inject

open class BookmarkProject @Inject constructor(
    private val projectsRepository: ProjectsRepository ,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<BookmarkProject.Params>(postExecutionThread) {

    data class Params constructor(val projectId :String){
        companion object{
            fun forProject(projectId: String) : Params{
                return Params(projectId)
            }
        }
    }

    override fun buildCompletableUseCase(params: Params?): Completable {
        if(params == null) throw IllegalArgumentException("params can't be null")
        return projectsRepository.bookmarkProject(params.projectId)
    }
}