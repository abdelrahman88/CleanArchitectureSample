package com.cleanarchitecture.cache.dao

import androidx.room.*
import com.cleanarchitecture.cache.db.ProjectConstants.DELETE_PROJECTS
import com.cleanarchitecture.cache.db.ProjectConstants.QUERY_BOOKMARKED_PROJECTS
import com.cleanarchitecture.cache.db.ProjectConstants.QUERY_PROJECTS
import com.cleanarchitecture.cache.db.ProjectConstants.QUERY_UPDATE_BOOKMARK_STATUS
import com.cleanarchitecture.cache.model.CachedProject
import io.reactivex.Flowable

@Dao
abstract class CachedProjectDao {

    @Query(QUERY_PROJECTS)
    abstract fun getProjects() : Flowable<List<CachedProject>>

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    abstract fun insertProjects( project: List<CachedProject>)

    @Query(DELETE_PROJECTS)
    abstract fun deleteProjects()

    @Query(QUERY_BOOKMARKED_PROJECTS)
    abstract fun getBookmarkedProjects() :Flowable<List<CachedProject>>

    @Query(QUERY_UPDATE_BOOKMARK_STATUS)
    abstract fun setBookmarkStatus(isBookmarked : Boolean , projectId : String)
}