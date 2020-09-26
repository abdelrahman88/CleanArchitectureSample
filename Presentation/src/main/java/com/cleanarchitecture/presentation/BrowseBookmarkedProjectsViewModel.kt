package com.cleanarchitecture.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cleanarchitecture.domain.interactor.bookmarked.GetBookmarkedProjects
import com.cleanarchitecture.domain.model.Project
import com.cleanarchitecture.presentation.mapper.ProjectViewMapper
import com.cleanarchitecture.presentation.model.ProjectView
import com.cleanarchitecture.presentation.state.Resource
import com.cleanarchitecture.presentation.state.ResourceState
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BrowseBookmarkedProjectsViewModel @Inject constructor(
    private val getBookmarkedProjects: GetBookmarkedProjects ,
    private val mapper: ProjectViewMapper
) : ViewModel() {

    private val liveData : MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    override fun onCleared() {
        getBookmarkedProjects.dispose()
        super.onCleared()
    }

    fun getBookmarkedProjects(): LiveData<Resource<List<ProjectView>>>{
        return liveData
    }

    fun fetchBookmarkedProjects(){
        liveData.postValue(Resource(ResourceState.LOADING , null ,null))
        return getBookmarkedProjects.execute(BookmarkedProjectsSubscriber())
    }

    inner class BookmarkedProjectsSubscriber : DisposableObserver<List<Project>>(){
        override fun onComplete() {
        }

        override fun onNext(t: List<Project>) {
            liveData.postValue(Resource(ResourceState.SUCCESS ,
           t.map {
               mapper.mapToView(it) } , null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR , null , e.localizedMessage))
        }

    }
}