package com.example.ecommmerceapp.di

import com.example.ecommmerceapp.data.Provider.DigitalInkProvider
import com.example.ecommmerceapp.data.Provider.DigitalInkProviderImpl
import com.example.ecommmerceapp.data.Service.ProductoService
import com.example.ecommmerceapp.data.Service.UsuarioService
import com.example.ecommmerceapp.data.repository.ProductoRepository
import com.example.ecommmerceapp.data.repository.UsuarioRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindDigitalInkProvider(provider: DigitalInkProviderImpl): DigitalInkProvider

    companion object{
        @Provides
        @Singleton
        fun provideUserService(): UsuarioService{
            return UsuarioService()
        }
        @Provides
        @Singleton
        fun provideUsuarioRepository(usuarioService: UsuarioService): UsuarioRepository{
            return UsuarioRepository(usuarioService)
        }


        @Provides
        @Singleton
        fun provideProductoService(): ProductoService{
            return ProductoService()
        }

        @Provides
        @Singleton
        fun provideProductoRepository(productoService: ProductoService): ProductoRepository{
            return ProductoRepository(productoService)
        }




    }
}