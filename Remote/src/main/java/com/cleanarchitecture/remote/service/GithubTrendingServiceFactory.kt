package com.cleanarchitecture.remote.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object GithubTrendingServiceFactory {

    fun makeGithubTrendingService(isDebug: Boolean): GithubTrendingService {
        return makeGithubTrendingService(makeOkHttpClient(makeLoggingInterceptor(isDebug)))
    }

    private fun  makeGithubTrendingService(okHttpClient: OkHttpClient) : GithubTrendingService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(GithubTrendingService::class.java)
    }

    private fun makeOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(120 , TimeUnit.SECONDS)
                .readTimeout(120 , TimeUnit.SECONDS)
                .build()
    }

    private fun makeLoggingInterceptor(isDebug : Boolean) : HttpLoggingInterceptor{
        val logging = HttpLoggingInterceptor()
        logging.level =  if(isDebug){
                HttpLoggingInterceptor.Level.BODY
        }
        else{
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}