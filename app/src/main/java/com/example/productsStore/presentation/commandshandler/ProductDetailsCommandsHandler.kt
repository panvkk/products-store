package com.example.productsStore.presentation.commandshandler

import com.example.productsStore.core.Resource
import com.example.productsStore.domain.usecase.GetProductDetailsUseCase
import com.example.productsStore.presentation.contract.ProductDetailsCommand
import com.example.productsStore.presentation.contract.ProductDetailsEvent
import com.example.productsStore.presentation.contract.ProductDetailsScreenState
import com.example.productsStore.presentation.mapper.toUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.tinkoff.kotea.core.CommandsFlowHandler
import javax.inject.Inject

class ProductDetailsCommandsHandler @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase
) : CommandsFlowHandler<ProductDetailsCommand, ProductDetailsEvent> {
    override fun handle(commands: Flow<ProductDetailsCommand>): Flow<ProductDetailsEvent> = flow {
        commands.collect { command ->
            when(command) {
                is ProductDetailsCommand.LoadProductDetails -> {
                    val newState = fetchProductDetails(command.productId)
                    emit(ProductDetailsEvent.Internal.DetailsLoaded(newState))
                }
            }
        }
    }

    private suspend fun fetchProductDetails(productId: Int) : ProductDetailsScreenState {
        val result = getProductDetailsUseCase.invoke(productId)

        return when (result) {
            is Resource.Success -> {
                ProductDetailsScreenState.Content(result.data.toUiModel())
            }
            is Resource.Error -> {
                ProductDetailsScreenState.Error(result.error)
            }
        }
    }
}