package com.cleanarchitecture.remote.service

import com.cleanarchitecture.remote.model.ProjectsResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubTrendingService  {

    @GET("search/repositories")
    fun searchRepositories(@Query ("q") query : String,
                            @Query("sort") sortBy : String ,
                            @Query("order") orderBy : String)
    : Observable<ProjectsResponseModel>
}