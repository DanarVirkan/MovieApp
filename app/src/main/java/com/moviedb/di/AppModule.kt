package com.moviedb.di

import com.example.core.domain.Interactor
import com.example.core.domain.UseCase
import com.moviedb.details.DetailsViewModel
import com.moviedb.explore.ExploreViewModel
import com.moviedb.trending.TrendingViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCase = module {
    factory<UseCase> { Interactor(get()) }
}

@InternalCoroutinesApi
val viewModelModule = module {
    viewModel { TrendingViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { ExploreViewModel(get()) }
}