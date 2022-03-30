package com.moviedb.bookmarked.di

import com.moviedb.bookmarked.BookmarkedViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@InternalCoroutinesApi
val bookmarkedModule = module {
    viewModel { BookmarkedViewModel(get()) }
}