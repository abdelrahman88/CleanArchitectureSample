package com.cleanarchitecture.cache

import com.cleanarchitecture.cache.db.ProjectsDatabase
import com.cleanarchitecture.cache.mapper.CachedProjectMapper
import com.cleanarchitecture.cache.model.Config
import com.cleanarchitecture.data.model.ProjectEntity
import com.cleanarchitecture.data.repository.ProjectsCache
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ProjectsCacheImpl @Inject constructor(
    private val projectDatabase: ProjectsDatabase,
    private val mapper: CachedProjectMapper
) : ProjectsCache {
    override fun clearProjects(): Completable {
        return Completable.defer {
            projectDatabase.cachedProjectDao().deleteProjects()
            Completable.complete()
        }
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        return Completable.defer {
            projectDatabase.cachedProjectDao().insertProjects(
                projects.map { mapper.mapToCached(it) })
            Completable.complete()
        }
    }

    override fun getProjects(): Observable<List<ProjectEntity>> {
        return projectDatabase.cachedProjectDao().getProjects()
            .toObservable().map {
                it.map { mapper.mapFromCached(it) }
            }
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        return projectDatabase.cachedProjectDao().getBookmarkedProjects()
            .toObservable().map {
                it.map { mapper.mapFromCached(it) }
            }
    }

    override fun setProjectAsBookmarked(projectID: String): Completable {
        return Completable.defer {
            projectDatabase.cachedProjectDao().setBookmarkStatus(true, projectID)
            Completable.complete()
        }
    }

    override fun setProjectAsUnBookmarked(projectID: String): Completable {
        return Completable.defer {
            projectDatabase.cachedProjectDao().setBookmarkStatus(false, projectID)
            Completable.complete()
        }
    }

    override fun areProjectsCashed(): Single<Boolean> {
        return projectDatabase.cachedProjectDao().getProjects().isEmpty.map {
            !it
        }
    }

    override fun setLastCashTime(lastCache: Long): Completable {
        return Completable.defer {
            projectDatabase.configDao().insertConfig(Config(lastCacheTime = lastCache))
            Completable.complete()
        }
    }

    override fun isProjectsCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()
        val expirationTime = (60 * 10 * 1000).toLong()
        return projectDatabase.configDao().getConfig()
            .onErrorReturn { Config(lastCacheTime = 0) }
            .toSingle(Config(lastCacheTime = 0L)) // a default value to emit if the source Publisher emits no item
            .map {
                currentTime - it.lastCacheTime > expirationTime
            }
    }
}
