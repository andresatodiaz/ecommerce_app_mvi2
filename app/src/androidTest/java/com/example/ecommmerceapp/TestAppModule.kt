package com.example.ecommmerceapp

import com.example.ecommmerceapp.data.Provider.DigitalInkProvider
import com.example.ecommmerceapp.data.Provider.DigitalInkProviderImpl
import com.example.ecommmerceapp.data.Service.ProductoService
import com.example.ecommmerceapp.data.Service.UsuarioService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
abstract class TestAppModule {
    @Binds
    @Singleton
    @Named("digitalInk")
    abstract fun bindDigitalInkProvider(provider: DigitalInkProviderImpl): DigitalInkProvider

    companion object{
        @Provides
        @Singleton
        @Named("userService")
        fun provideUserService(): UsuarioService {
            return UsuarioService()
        }

        @Provides
        @Singleton
        @Named("productoService")
        fun provideProductoService(): ProductoService {
            return ProductoService()
        }
    }
}