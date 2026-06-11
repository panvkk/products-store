package com.example.productsStore.core.di

import com.example.productsStore.core.logger.AppLogger
import com.example.productsStore.core.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.time.Clock

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    @Singleton
    fun provideLogger() : Logger = AppLogger()

    @Provides
    @Singleton
    fun provideClock() : Clock = Clock.System

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Provides
    @ApplicationMainScope
    fun provideApplicationMainScope() : CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationMainScope