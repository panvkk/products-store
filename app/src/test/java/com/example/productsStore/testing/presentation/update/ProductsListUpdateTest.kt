package com.example.productsStore.testing.presentation.update

import com.example.productsStore.core.domain.DomainError
import com.example.productsStore.presentation.contract.ProductsListEvent
import com.example.productsStore.presentation.contract.ProductsListNews
import com.example.productsStore.presentation.contract.ProductsListState
import com.example.productsStore.presentation.model.ProductUiModel
import com.example.productsStore.presentation.update.ProductsListUpdate
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ProductsListUpdateTest {

    private val initialState = ProductsListState(
        isLoadingGoing = true,
        isLastPageReached = false,
        productItems = emptyList(),
        pageSize = 1,
        error = null
    )
    private val testProducts = listOf(
        ProductUiModel(
            id = 1,
            productTitle = "productTitle",
            priceInUSD = 1f,
            brand = ""
        )
    )

    @Test
    fun `GIVEN success loading WHEN NextPageLoaded THEN state correctly updates`() {
        // GIVEN
        val expectedScreenState = initialState.copy(isLoadingGoing = false, productItems = testProducts)
        val event = ProductsListEvent.Internal.NextPageLoaded(testProducts, false, null)

        // WHEN
        val actualState: ProductsListState? = createUpdate().update(initialState, event).state

        // THEN
        assertNotNull(actualState)
        assertEquals(expectedScreenState, actualState)
    }

    @Test
    fun `GIVEN network error WHEN NextPageLoaded THEN state correctly updates`() {
        // GIVEN
        val expectedError = DomainError.NetworkIssue
        val expectedScreenState = initialState.copy(isLoadingGoing = false, error = expectedError)
        val event = ProductsListEvent.Internal.NextPageLoaded(emptyList(), false, expectedError)

        // WHEN
        val actualState: ProductsListState? = createUpdate().update(initialState, event).state

        // THEN
        assertNotNull(actualState)
        assertEquals(expectedScreenState, actualState)
    }

    @Test
    fun `GIVEN state WHEN OnNavigateToDetails THEN news correctly updates`() {
        // GIVEN
        val productId = 52
        val event = ProductsListEvent.Ui.OnNavigateDetails(productId)
        val expectedNew = ProductsListNews.NavigateToDetails(productId)

        // WHEN
        val actualNews: List<ProductsListNews> = createUpdate().update(initialState, event).news

        // THEN
        assertTrue(actualNews.contains(expectedNew))
    }

    @Test
    fun `GIVEN state WHEN AddedToCart THEN news correctly updates`() {
        // GIVEN
        val initialState = ProductsListState(
            isLoadingGoing = true,
            isLastPageReached = false,
            productItems = emptyList(),
            pageSize = 10
        )
        val expectedTitle = "title"
        val event = ProductsListEvent.Internal.AddedToCart(expectedTitle)
        val expectedNew = ProductsListNews.ShowAddedToCartToast(expectedTitle)

        // WHEN
        val actualNews: List<ProductsListNews> = createUpdate().update(initialState, event).news

        // THEN
        assertTrue(actualNews.contains(expectedNew))
    }

    private fun createUpdate() = ProductsListUpdate()
}