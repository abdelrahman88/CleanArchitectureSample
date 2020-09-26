package com.cleanarchitecture.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cleanarchitecture.cache.db.ConfigConstants
import com.cleanarchitecture.cache.model.Config
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
abstract class ConfigDao {
    @Query(ConfigConstants.QUERY_CONFIG)
    abstract fun getConfig() : Maybe<Config>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertConfig(config: Config)
}