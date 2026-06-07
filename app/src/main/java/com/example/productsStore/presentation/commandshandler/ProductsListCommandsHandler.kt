package com.example.productsStore.presentation.commandshandler

import com.example.productsStore.core.Resource
import com.example.productsStore.core.di.ApplicationScope
import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.domain.usecase.AddToCartUseCase
import com.example.productsStore.domain.usecase.GetProductsUseCase
import com.example.productsStore.presentation.contract.ProductsListCommand
import com.example.productsStore.presentation.contract.ProductsListEvent
import com.example.productsStore.presentation.mapper.toUiModel
import com.example.productsStore.presentation.model.ProductUiModel
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
                    val result = fetchNextPage(command.skip, command.limit)
                    emit(
                        ProductsListEvent.Internal.NextPageLoaded(
                            newProducts = result.newProducts,
                            isLastPage = result.isLastPage,
                            error = result.error
                        )
                    )
                }
                is ProductsListCommand.AddToCart -> {
                    addToCart(command.productId, command.productTitle)
                    emit(ProductsListEvent.Internal.AddedToCart)
                }
            }
        }
    }

    private fun addToCart(productId: Int, productTitle: String) {
        applicationScope.launch {
            addToCartUseCase.invoke(productId, productTitle)
        }
    }

    private suspend fun fetchNextPage(skip: Int, limit: Int) : FetchingNextPageResult {
        val result = getProductsUseCase.invoke(skip = skip, limit = limit)

        return when(result) {
            is Resource.Success -> {
                val newProducts = result.data.map { it.toUiModel() }
                FetchingNextPageResult(
                    newProducts = newProducts,
                    isLastPage = newProducts.size < limit,
                    error = null
                )
            }
            is Resource.Error -> {
                FetchingNextPageResult(emptyList(), false, result.error)
            }
        }
    }

    private data class FetchingNextPageResult(
        val newProducts: List<ProductUiModel>,
        val isLastPage: Boolean,
        val error: DomainError? = null
    )
}