package com.cleanarchitecture.remote.mapper

import com.cleanarchitecture.data.model.ProjectEntity
import com.cleanarchitecture.remote.model.ProjectModel
import javax.inject.Inject

class ProjectResponseModelMapper @Inject constructor(): ModelMapper<ProjectModel, ProjectEntity> {

    override fun mapFromModel(model: ProjectModel): ProjectEntity {
        return ProjectEntity(
            model.id, model.name, model.fullName, model.starCount.toString(), model.dateCreated
            , model.owner.ownerName, model.owner.ownerAvatar 
        )
    }
}