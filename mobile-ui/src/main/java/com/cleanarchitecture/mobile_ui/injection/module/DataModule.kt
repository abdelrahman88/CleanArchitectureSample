package com.cleanarchitecture.mobile_ui.injection.module

import com.cleanarchitecture.data.ProjectsDataRepository
import com.cleanarchitecture.domain.repository.ProjectsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: ProjectsDataRepository): ProjectsRepository

}