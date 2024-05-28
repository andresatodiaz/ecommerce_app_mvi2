package com.mvi.ecommmerceapp.di

import com.mvi.ecommmerceapp.data.DataSource.MLKit.InkProvider.DigitalInkProvider
import com.mvi.ecommmerceapp.data.DataSource.MLKit.InkProvider.DigitalInkProviderImpl
import com.mvi.ecommmerceapp.data.Service.ProductoService
import com.mvi.ecommmerceapp.data.Service.UsuarioService
import com.mvi.ecommmerceapp.data.repository.ProductoRepositoryImpl
import com.mvi.ecommmerceapp.data.repository.UsuarioRepositoryImpl
import com.mvi.ecommmerceapp.domain.Repository.UsuarioRepository
import com.mvi.ecommmerceapp.domain.Repository.ProductoRepository
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
        fun provideUsuarioRepository(usuarioService: UsuarioService): UsuarioRepository {
            return UsuarioRepositoryImpl(usuarioService)
        }


        @Provides
        @Singleton
        fun provideProductoService(): ProductoService{
            return ProductoService()
        }

        @Provides
        @Singleton
        fun provideProductoRepository(productoService: ProductoService): ProductoRepository {
            return ProductoRepositoryImpl(productoService)
        }



    }
}