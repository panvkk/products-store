package com.example.productsStore.core.di

import com.example.productsStore.core.logger.AppLogger
import com.example.productsStore.core.logger.LoggingProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    fun provideLogger() : LoggingProvider = AppLogger()
}