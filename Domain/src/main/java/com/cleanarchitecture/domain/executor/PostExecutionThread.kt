package com.cleanarchitecture.domain.executor
import io.reactivex.Scheduler
interface PostExecutionThread {
    val scheduler :Scheduler
}