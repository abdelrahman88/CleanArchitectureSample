package com.cleanarchitecture.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleanarchitecture.domain.interactor.bookmarked.BookmarkProject
import com.cleanarchitecture.domain.interactor.bookmarked.UnBookmarkProject
import com.cleanarchitecture.domain.interactor.browse.GetProjects
import com.cleanarchitecture.domain.model.Project
import com.cleanarchitecture.presentation.mapper.ProjectViewMapper
import com.cleanarchitecture.presentation.model.ProjectView
import com.cleanarchitecture.presentation.state.Resource
import com.cleanarchitecture.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseProjectsViewModel @Inject constructor(
    private val getProjects: GetProjects ,
    private val bookmarkProject: BookmarkProject,
    private val unBookmarkProject: UnBookmarkProject,
    private val mapper : ProjectViewMapper
) : ViewModel() {

    private val liveData : MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    init {
        fetchProjects()
    }
    override fun onCleared() {
        getProjects.dispose()
        super.onCleared()
    }

    fun getProjects() : LiveData<Resource<List<ProjectView>>> {
        return liveData
    }

    fun fetchProjects() {
        liveData.postValue(Resource(ResourceState.LOADING , null , null))
        getProjects.execute(ProjectsSubscriber())
    }

    fun bookmarkProject(projectId : String){
        liveData.postValue(Resource(ResourceState.LOADING, null , null))
        return bookmarkProject.execute(BookmarkProjectsSubscriber() ,
        BookmarkProject.Params.forProject(projectId))
    }

    fun unBookmarkProject(projectId : String){
        liveData.postValue(Resource(ResourceState.LOADING, null , null))
        return unBookmarkProject.execute(BookmarkProjectsSubscriber() ,
        UnBookmarkProject.Params.forProject(projectId))
    }

    inner class ProjectsSubscriber : DisposableObserver<List<Project>>(){
        override fun onComplete() {
        }

        override fun onNext(t: List<Project>) {
            liveData.postValue(Resource(ResourceState.SUCCESS ,
            t.map {
                mapper.mapToView(it) }, null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR ,null ,e.localizedMessage))
        }

    }

    inner class BookmarkProjectsSubscriber() : DisposableCompletableObserver(){
        override fun onComplete() {
            liveData.postValue(Resource(ResourceState.SUCCESS ,liveData.value?.data ,null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR ,liveData.value?.data  ,e.localizedMessage))
        }

    }
}