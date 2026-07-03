package com.example.productsStore.presentation.commandshandler

import com.example.productsStore.core.DELAY_AFTER_ADDING_TO_CART
import com.example.productsStore.core.Resource
import com.example.productsStore.core.di.ApplicationScope
import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.domain.usecase.GetProductsUseCase
import com.example.productsStore.domain.usecase.cart.AddToCartUseCase
import com.example.productsStore.presentation.contract.ProductsListCommand
import com.example.productsStore.presentation.contract.ProductsListEvent
import com.example.productsStore.presentation.contract.ProductsListEvent.Internal.AddedToCart
import com.example.productsStore.presentation.contract.ProductsListEvent.Internal.NextPageLoaded
import com.example.productsStore.presentation.contract.ProductsListEvent.Internal.UpdateAddToCartClickability
import com.example.productsStore.presentation.mapper.toDomain
import com.example.productsStore.presentation.mapper.toUiModel
import com.example.productsStore.presentation.model.ProductUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class ProductsListCommandsHandler @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    @ApplicationScope
    private val applicationScope: CoroutineScope
) : CommandsFlowHandler<ProductsListCommand, ProductsListEvent> {
    override fun handle(commands: Flow<ProductsListCommand>): Flow<ProductsListEvent> =
        commands.flatMapMerge { command ->
            flow {
                when(command) {
                    is ProductsListCommand.FetchNextPage -> {
                        emit(ProductsListEvent.Internal.LoadingStarted)
                        val result = fetchNextPage(command.skip, command.limit)
                        emit(
                            NextPageLoaded(
                                newProducts = result.newProducts,
                                isLastPage = result.isLastPage,
                                error = result.error
                            )
                        )
                    }
                    is ProductsListCommand.AddToCart -> {
                        addToCart(command.productUiModel)
                        emit(
                            AddedToCart(
                                addedProductTitle = command.productUiModel.productTitle)
                        )
                    }
                    is ProductsListCommand.DelayAddToCartClickability -> {
                        emit(UpdateAddToCartClickability(command.itemId, isClickable = false))

                        delay(DELAY_AFTER_ADDING_TO_CART.milliseconds)

                        emit(UpdateAddToCartClickability(command.itemId, isClickable = true))
                    }
                }
        }
    }

    private fun addToCart(productUiModel: ProductUiModel) {
        applicationScope.launch {
            addToCartUseCase.invoke(productUiModel.toDomain())
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