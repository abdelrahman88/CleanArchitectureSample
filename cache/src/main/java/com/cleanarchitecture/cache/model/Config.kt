package com.cleanarchitecture.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cleanarchitecture.cache.db.ConfigConstants

@Entity(tableName = ConfigConstants.TABLE_NAME)
 class Config(
             @PrimaryKey(autoGenerate = true)
              var id: Int = -1,
             var lastCacheTime : Long) {
}