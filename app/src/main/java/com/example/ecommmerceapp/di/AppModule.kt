package com.example.ecommmerceapp.di

import com.example.ecommmerceapp.data.Service.ProductoService
import com.example.ecommmerceapp.data.Service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideUserService(): UserService{
        return UserService()
    }

    @Provides
    @Singleton
    fun provideProductoService(): ProductoService{
        return ProductoService()
    }
}