package com.example.tmdb.data.di

import com.example.tmdb.data.MovieRepository
import com.example.tmdb.data.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindsMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository
}

