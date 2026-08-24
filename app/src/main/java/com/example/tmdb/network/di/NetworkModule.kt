package com.example.tmdb.network.di

import com.example.tmdb.network.AuthInterceptor
import com.example.tmdb.network.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import retrofit2.converter.kotlinx.serialization.asConverterFactory


@Module
@InstallIn(SingletonComponent::class)

class NetworkModule {
    @Provides
    @Singleton
    fun providesJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

        @Provides
        @Singleton
        fun okhttpCallFactory(): Call.Factory {
            return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        },
                )
                .build()
        }


        @Provides
        @Singleton
        fun providesRetrofit(
            networkJson: Json,
            okhttpCallFactory: dagger.Lazy<okhttp3.Call.Factory>,
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .callFactory {
                    okhttpCallFactory.get().newCall(it)
                }
                .addConverterFactory(
                    networkJson.asConverterFactory(
                        "application/json".toMediaType()
                    )
                )
                .build()

        }

        @Provides
        @Singleton
        fun providesMovieService(retrofit: Retrofit): MovieService {
            return retrofit.create(MovieService::class.java)
        }
    }



private const val BASE_URL = "https://api.themoviedb.org/3/"
