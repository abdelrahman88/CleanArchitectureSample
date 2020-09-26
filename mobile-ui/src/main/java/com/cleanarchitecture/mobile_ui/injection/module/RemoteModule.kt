package com.cleanarchitecture.mobile_ui.injection.module

import com.cleanarchitecture.data.repository.ProjectsRemote
import com.cleanarchitecture.mobile_ui.BuildConfig
import com.cleanarchitecture.remote.ProjectsRemoteImpl
import com.cleanarchitecture.remote.service.GithubTrendingService
import com.cleanarchitecture.remote.service.GithubTrendingServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideGithubService(): GithubTrendingService {
            return GithubTrendingServiceFactory.makeGithubTrendingService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindProjectsRemote(projectsRemote: ProjectsRemoteImpl): ProjectsRemote
}