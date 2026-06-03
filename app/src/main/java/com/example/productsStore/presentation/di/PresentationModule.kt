package com.example.productsStore.presentation.di

import com.example.productsStore.core.di.ApplicationScope
import com.example.productsStore.presentation.commandshandler.ProductsListCommandsHandler
import com.example.productsStore.presentation.contract.ProductsListCommand
import com.example.productsStore.presentation.contract.ProductsListEvent
import com.example.productsStore.presentation.contract.ProductsListNews
import com.example.productsStore.presentation.contract.ProductsListState
import com.example.productsStore.presentation.update.ProductsListUpdate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton
import ru.tinkoff.kotea.core.KoteaStore
import ru.tinkoff.kotea.core.Store
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {
    @Provides
    @Singleton
    fun provideProductsListStore(
        productsListCommandsHandler: ProductsListCommandsHandler
    ) : Store<ProductsListState, ProductsListEvent.Ui, ProductsListNews> =
        KoteaStore(
            initialState = ProductsListState(
                isLoadingGoing = false,
                isLastPageReached = false,
                products = emptyList(),
                pageSize = 0
            ),
            initialCommands = emptyList(),
            commandsFlowHandlers = listOf(productsListCommandsHandler),
            update = ProductsListUpdate()
        )
}