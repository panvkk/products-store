package com.example.productsStore.testing.presentation.update

import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.presentation.contract.CartEvent
import com.example.productsStore.presentation.contract.CartNews
import com.example.productsStore.presentation.contract.CartState
import com.example.productsStore.presentation.contract.ProductsListEvent
import com.example.productsStore.presentation.contract.ProductsListNews
import com.example.productsStore.presentation.contract.ProductsListState
import com.example.productsStore.presentation.model.CartItemUiModel
import com.example.productsStore.presentation.model.ProductUiModel
import com.example.productsStore.presentation.update.CartUpdate
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class CartUpdateTest {

    private val initialState = CartState.Loading
    private val testCartItems = listOf(
        CartItemUiModel(
            ProductUiModel(
                id = 1,
                productTitle = "productTitle",
                priceInUSD = 1f,
                brand = ""
            ),
            quantity = 1
        )
    )
    private val testCartSize = 1
    private val testNetworkError = DomainError.NetworkIssue

    @Test
    fun `GIVEN success loading WHEN CartLoaded THEN state correctly updates`() {
        // GIVEN
        val expectedScreenState = CartState.Content(cartItems = testCartItems, cartSize = testCartSize)
        val event = CartEvent.Internal.CartLoaded(expectedScreenState)

        // WHEN
        val actualState: CartState? = createUpdate().update(initialState, event).state

        // THEN
        assertNotNull(actualState)
        assertEquals(expectedScreenState, actualState)
    }

    @Test
    fun `GIVEN network error WHEN CartLoaded THEN state correctly updates`() {
        // GIVEN
        val expectedScreenState = CartState.Error(testNetworkError)
        val event = CartEvent.Internal.CartLoaded(expectedScreenState)

        // WHEN
        val actualState: CartState? = createUpdate().update(initialState, event).state

        // THEN
        assertNotNull(actualState)
        assertEquals(expectedScreenState, actualState)
    }

    @Test
    fun `GIVEN state WHEN CartCleared THEN news correctly updates`() {
        // GIVEN
        val event = CartEvent.Internal.CartCleared
        val expectedNew = CartNews.ShowCartClearedToast

        // WHEN
        val actualNews: List<CartNews> = createUpdate().update(initialState, event).news

        // THEN
        assertTrue(actualNews.contains(expectedNew))
    }

    @Test
    fun `GIVEN state WHEN OnNavigateToDetails THEN news correctly updates`() {
        // GIVEN
        val productId = 52
        val event = CartEvent.Ui.OnNavigateToDetails(productId)
        val expectedNew = CartNews.NavigateToDetails(productId)

        // WHEN
        val actualNews: List<CartNews> = createUpdate().update(initialState, event).news

        // THEN
        assertTrue(actualNews.contains(expectedNew))
    }

    private fun createUpdate() = CartUpdate()
}