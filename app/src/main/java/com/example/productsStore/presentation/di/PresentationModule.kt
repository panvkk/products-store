package com.example.productsStore.presentation.di

import com.example.productsStore.presentation.commandshandler.CartCommandsHandler
import com.example.productsStore.presentation.commandshandler.ProductDetailsCommandsHandler
import com.example.productsStore.presentation.commandshandler.ProductsListCommandsHandler
import com.example.productsStore.presentation.contract.CartCommand
import com.example.productsStore.presentation.contract.CartEvent
import com.example.productsStore.presentation.contract.CartNews
import com.example.productsStore.presentation.contract.CartState
import com.example.productsStore.presentation.contract.ProductDetailsEvent
import com.example.productsStore.presentation.contract.ProductDetailsNews
import com.example.productsStore.presentation.contract.ProductDetailsScreenState
import com.example.productsStore.presentation.contract.ProductDetailsState
import com.example.productsStore.presentation.contract.ProductsListEvent
import com.example.productsStore.presentation.contract.ProductsListNews
import com.example.productsStore.presentation.contract.ProductsListState
import com.example.productsStore.presentation.update.CartUpdate
import com.example.productsStore.presentation.update.ProductDetailsUpdate
import com.example.productsStore.presentation.update.ProductsListUpdate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ru.tinkoff.kotea.core.KoteaStore
import ru.tinkoff.kotea.core.Store

@Module
@InstallIn(ViewModelComponent::class)
object PresentationModule {
    @Provides
    @ViewModelScoped
    fun provideProductsListStore(
        productsListCommandsHandler: ProductsListCommandsHandler
    ) : Store<ProductsListState, ProductsListEvent.Ui, ProductsListNews> =
        KoteaStore(
            initialState = ProductsListState(
                isLoadingGoing = false,
                isLastPageReached = false,
                productItems = emptyList(),
                pageSize = 0
            ),
            initialCommands = emptyList(),
            commandsFlowHandlers = listOf(productsListCommandsHandler),
            update = ProductsListUpdate()
        )

    @Provides
    @ViewModelScoped
    fun provideProductDetailsStore(
        productDetailsCommandsHandler: ProductDetailsCommandsHandler
    ) : Store<ProductDetailsState, ProductDetailsEvent.Ui, ProductDetailsNews> =
        KoteaStore(
            initialState = ProductDetailsState(
                null,
                ProductDetailsScreenState.Loading
            ),
            initialCommands = emptyList(),
            commandsFlowHandlers = listOf(productDetailsCommandsHandler),
            update = ProductDetailsUpdate()
        )

    @Provides
    @ViewModelScoped
    fun provideCartStore(
        cartCommandsHandler: CartCommandsHandler
    ) : Store<CartState, CartEvent.Ui, CartNews> =
        KoteaStore(
            initialState = CartState.Loading,
            initialCommands = listOf(CartCommand.LoadCart, CartCommand.TryShowCartHint),
            commandsFlowHandlers = listOf(cartCommandsHandler),
            update = CartUpdate()
        )
}