package com.example.productsStore.presentation.di

import com.example.productsStore.presentation.background.manager.CartAlarmManager
import com.example.productsStore.presentation.background.manager.CartAlarmManagerImpl
import com.example.productsStore.presentation.background.notification.CartNotifier
import com.example.productsStore.presentation.background.notification.CartNotifierImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BackgroundModule {
    @Singleton
    @Binds
    abstract fun bindCartNotifier(cartNotifierImpl: CartNotifierImpl) : CartNotifier

    @Singleton
    @Binds
    abstract fun bindCartAlarmManager(cartAlarmManagerImpl: CartAlarmManagerImpl) : CartAlarmManager

}