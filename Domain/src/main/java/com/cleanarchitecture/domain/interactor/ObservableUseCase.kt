package com.cleanarchitecture.domain.interactor

import com.cleanarchitecture.domain.executor.PostExecutionThread
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class ObservableUseCase <T ,in Params > constructor(private val postExecutionThread: PostExecutionThread){

    private val disposables = CompositeDisposable()
   protected abstract fun buildObservableUseCase(params: Params? = null) : Observable<T>

    open fun execute(observer: DisposableObserver<T> , params: Params? = null) {
        val observable = this.buildObservableUseCase(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)

            addDisposable(observable.subscribeWith(observer))
    }

    fun dispose(){
        disposables.dispose()
    }

    private fun addDisposable(disposable: Disposable){
        disposables.add(disposable)
    }
}