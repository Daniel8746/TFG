package com.pmdm.casino.di

import com.pmdm.casino.data.services.apuestas.ApuestasService
import com.pmdm.casino.data.services.blackJack.BlackJackService
import com.pmdm.casino.data.services.interceptors.AuthInterceptor
import com.pmdm.casino.data.services.juegos.JuegosService
import com.pmdm.casino.data.services.usuario.UsuarioService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

        val timeout = 10L
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("http://192.168.100.9:8080/casino/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideUsuarioService(
        retrofit: Retrofit
    ): UsuarioService = retrofit.create(UsuarioService::class.java)

    @Provides
    @Singleton
    fun provideJuegosService(
        retrofit: Retrofit
    ): JuegosService = retrofit.create(JuegosService::class.java)

    @Provides
    @Singleton
    fun provideBlackJackService(
        retrofit: Retrofit
    ): BlackJackService = retrofit.create(BlackJackService::class.java)

    @Provides
    @Singleton
    fun provideApuestasService(
        retrofit: Retrofit
    ): ApuestasService = retrofit.create(ApuestasService::class.java)
}