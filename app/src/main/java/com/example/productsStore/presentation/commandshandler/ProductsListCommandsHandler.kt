package com.example.productsStore.presentation.commandshandler

import com.example.productsStore.core.Resource
import com.example.productsStore.core.di.ApplicationScope
import com.example.productsStore.domain.usecase.AddToCartUseCase
import com.example.productsStore.domain.usecase.GetProductsUseCase
import com.example.productsStore.presentation.contract.ProductsListCommand
import com.example.productsStore.presentation.contract.ProductsListEvent
import com.example.productsStore.presentation.contract.ProductsListState
import com.example.productsStore.presentation.mapper.toUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class ProductsListCommandsHandler @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    @ApplicationScope
    private val applicationScope: CoroutineScope
) : CommandsFlowHandler<ProductsListCommand, ProductsListEvent> {
    override fun handle(commands: Flow<ProductsListCommand>): Flow<ProductsListEvent> = flow {
        commands.collect { command ->
            when(command) {
                is ProductsListCommand.FetchNextPage -> {
                    emit(ProductsListEvent.Internal.LoadingStarted)
                    val newState = fetchNextPage(command.state)
                    emit(ProductsListEvent.Internal.NextPageLoaded(newState))
                }
                is ProductsListCommand.AddToCart -> {
                    addToCart(command.productId)
                    emit(ProductsListEvent.Internal.AddedToCart)
                }
            }
        }
    }

    private fun addToCart(productId: Int) {
        applicationScope.launch {
            addToCartUseCase.invoke(productId)
        }
    }

    private suspend fun fetchNextPage(state: ProductsListState) : ProductsListState {
        val skip = state.products.size
        val limit = state.pageSize

        val result = getProductsUseCase.invoke(skip = skip, limit = limit)

        return when(result) {
            is Resource.Success -> {
                val newProducts = result.data.map { it.toUiModel() }
                state.copy(
                    products = state.products + newProducts,
                    isLoadingGoing = false,
                    isLastPageReached = newProducts.size < limit,
                    error = null
                )
            }
            is Resource.Error -> {
                state.copy(
                    isLoadingGoing = false,
                    error = result.error
                )
            }
        }
    }
}