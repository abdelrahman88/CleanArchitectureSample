package com.cleanarchitecture.remote.model

import com.cleanarchitecture.remote.model.OwnerModel
import com.google.gson.annotations.SerializedName

class ProjectModel (val id : String ,val name :String ,
                    @SerializedName("full_name") val fullName : String ,
                   @SerializedName("stargazers_count") val starCount :Int ,
                    @SerializedName("created_at") val dateCreated :String
                    , val owner: OwnerModel
) {
}