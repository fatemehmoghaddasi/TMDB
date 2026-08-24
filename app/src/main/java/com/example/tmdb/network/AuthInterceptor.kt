package com.example.tmdb.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3MzVlYzdlNTYwMzYxOGM2ZjU5MjBiYjUyMmQwNTQxYyIsIm5iZiI6MTc4NTUwNDExOS4wMTYsInN1YiI6IjZhNmNhMTc3YzQ2ZDQ3NjMwYjc3MWEyMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.WldH9wjIad-sYpZva0KaRvJSWsMSaj7TyJRKD26CtO0"
        val newRequest = originalRequest.newBuilder()

                .addHeader("Authorization", "Bearer $token")
                .build()

        return chain.proceed(newRequest)
    }
}


