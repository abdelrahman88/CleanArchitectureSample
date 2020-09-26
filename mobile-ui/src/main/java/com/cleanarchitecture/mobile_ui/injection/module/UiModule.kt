package com.cleanarchitecture.mobile_ui.injection.module

import com.cleanarchitecture.domain.executor.PostExecutionThread
import com.cleanarchitecture.mobile_ui.browse.BrowseActivity
import com.cleanarchitecture.mobile_ui.UiThread
import com.cleanarchitecture.mobile_ui.bookmarked.BookmarkedActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {
    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesBrowseActivity(): BrowseActivity

    @ContributesAndroidInjector
    abstract fun contributesBookmarkedActivity(): BookmarkedActivity
}