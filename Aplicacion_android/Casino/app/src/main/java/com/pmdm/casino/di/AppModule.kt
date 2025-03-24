package com.pmdm.casino.di

import android.content.Context
import com.pmdm.casino.data.room.CasinoDb
import com.pmdm.casino.data.room.usuario.UsuarioDao
import com.pmdm.casino.data.services.usuario.UsuarioService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCasinoDatabase(
        @ApplicationContext context: Context
    ) : CasinoDb = CasinoDb.getDatabase(context)

    @Provides
    @Singleton
    fun provideUsuarioDao(
        db: CasinoDb
    ) : UsuarioDao = db.usuarioDao()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

        val timeout = 10L
        return OkHttpClient.Builder()
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
    ) : Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("http://192.168.100.9:8080/casino/api/usuario/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideContactoService(
        retrofit: Retrofit
    ) : UsuarioService = retrofit.create(UsuarioService::class.java)
}