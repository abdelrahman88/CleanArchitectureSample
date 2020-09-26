package com.cleanarchitecture.cache.mapper

interface CacheMapper <C , E>{

    fun mapFromCached(type : C) : E

    fun mapToCached(type : E) : C
}