package com.cleanarchitecture.data.repository

import com.cleanarchitecture.data.model.ProjectEntity
import io.reactivex.Observable


interface ProjectsRemote {

    fun getProjects() : Observable<List<ProjectEntity>>
}