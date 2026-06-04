package com.example.productsStore.testing.presentation.update

import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.presentation.contract.ProductDetailsCommand
import com.example.productsStore.presentation.contract.ProductDetailsEvent
import com.example.productsStore.presentation.contract.ProductDetailsScreenState
import com.example.productsStore.presentation.contract.ProductDetailsState
import com.example.productsStore.presentation.model.ProductDetailsUiModel
import com.example.productsStore.presentation.update.ProductDetailsUpdate
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ProductDetailsUpdateTest {

    @Test
    fun `GIVEN success loading WHEN DetailsLoaded THEN state correctly updates`() {
        // GIVEN
        val initialState = ProductDetailsState(1, screenState = ProductDetailsScreenState.Loading)
        val expectedScreenState = ProductDetailsScreenState.Content(
            ProductDetailsUiModel(
                productTitle = "detailsTitle",
                description = "",
                mainImageUri = null,
                rating = 1f,
                priceInUSD = 1f,
                weight = 1,
                availabilityStatus = "",
                warrantyInformation = "",
                isExpiredInfo = false
            )
        )
        val event = ProductDetailsEvent.Internal.DetailsLoaded(expectedScreenState)

        // WHEN
        val actualState: ProductDetailsState? = createUpdate().update(initialState, event).state

        // THEN
        assertNotNull(actualState)
        assertEquals(expectedScreenState, actualState.screenState)
    }

    @Test
    fun `GIVEN network error WHEN DetailsLoaded THEN state correctly updates`() {
        // GIVEN
        val initialState = ProductDetailsState(1, screenState = ProductDetailsScreenState.Loading)
        val expectedError = DomainError.NetworkIssue
        val expectedScreenState = ProductDetailsScreenState.Error(expectedError)
        val event = ProductDetailsEvent.Internal.DetailsLoaded(expectedScreenState)

        // WHEN
        val actualState: ProductDetailsState? = createUpdate().update(initialState, event).state

        // THEN
        assertNotNull(actualState)
        assertEquals(expectedScreenState, actualState.screenState)
    }

    @Test
    fun `WHEN OnRefreshDetails THEN LoadDetails command correctly triggers`() {
        // GIVEN
        val productId = 52
        val initialState = ProductDetailsState(
            productId,
            screenState = ProductDetailsScreenState.Error(DomainError.NetworkIssue)
        )
        val expectedCommand = ProductDetailsCommand.LoadProductDetails(productId)
        val event = ProductDetailsEvent.Ui.OnRefreshDetails(productId)

        // WHEN
        val actualCommands: List<ProductDetailsCommand> = createUpdate().update(initialState, event).commands

        // THEN
        assertTrue(actualCommands.contains(expectedCommand))
    }

    private fun createUpdate() = ProductDetailsUpdate()
}