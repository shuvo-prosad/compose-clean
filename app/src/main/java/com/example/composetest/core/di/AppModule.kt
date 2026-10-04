package com.example.composetest.core.di

import com.example.composetest.data.data_source.PhotosRemoteDataSource
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
@ComponentScan("com.example.composetest")
class AppModule {

    @Single
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true   // don't crash when the API adds fields
        coerceInputValues = true   // null/unknown enum -> default value
        explicitNulls = false
    }

    @Single
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )

            }
            .build()

    @Single
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Single
    fun provideUserRemoteDataSource(retrofit: Retrofit): PhotosRemoteDataSource =
        retrofit.create(PhotosRemoteDataSource::class.java)
}