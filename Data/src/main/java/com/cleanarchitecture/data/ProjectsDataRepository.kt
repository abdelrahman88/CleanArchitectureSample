package com.cleanarchitecture.data

import com.cleanarchitecture.data.mapper.ProjectMapper
import com.cleanarchitecture.data.model.ProjectEntity
import com.cleanarchitecture.data.repository.ProjectsCache
import com.cleanarchitecture.data.store.ProjectsDataStoreFactory
import com.cleanarchitecture.domain.model.Project
import com.cleanarchitecture.domain.repository.ProjectsRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ProjectsDataRepository @Inject constructor(
    private val mapper: ProjectMapper
    , private val cache: ProjectsCache
, private val factory: ProjectsDataStoreFactory
) : ProjectsRepository {

    override fun getProjects(): Observable<List<Project>> {
        return Observable.zip(cache.areProjectsCashed().toObservable()
                             ,cache.isProjectsCacheExpired().toObservable(),
            BiFunction <Boolean,Boolean, Pair<Boolean,Boolean>>{areCashed , isExpired ->
                    Pair(areCashed, isExpired)
            } )
            .flatMap{
                 factory.getDataStore(it.first , it.second).getProjects()
            }
            .flatMap{projects ->
                factory.getCacheDataStore().saveProjects(projects).andThen(Observable.just(projects))
          }
            .map { it ->
                it.map {
                    mapper.mapFromEntity(it)
                }
            }
    }

    override fun bookmarkProject(projectId: String): Completable {
       return factory.getCacheDataStore().setProjectAsBookmarked(projectId)
    }

    override fun unBookmarkProject(projectId: String): Completable {
        return factory.getCacheDataStore().setProjectAsUnBookmarked(projectId)
    }

    override fun getBookmarkedProjects(): Observable<List<Project>> {
        return factory.getCacheDataStore().getBookmarkedProjects().map {
            it.map {
                mapper.mapFromEntity(it)
            }
            // another way
//            mapToEntity(it)
        }
    }

    fun mapToEntity(projects : List<ProjectEntity>) : List<Project>{
        var list = mutableListOf<Project>()
        for(project in projects){
           list.add( mapper.mapFromEntity(project))
        }
        return list
    }
}