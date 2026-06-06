package com.example.productsStore.testing.ui.test

import android.app.Application
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.productsStore.presentation.contract.CartState
import com.example.productsStore.presentation.model.CartItemUiModel
import com.example.productsStore.presentation.model.ProductUiModel
import com.example.productsStore.presentation.ui.screen.CartScreenContent
import com.example.productsStore.presentation.ui.screen.testing.LazyListItemPosition
import com.example.productsStore.presentation.ui.screen.testing.testtags.CartScreenTestTags
import com.example.productsstrore.R
import com.kaspersky.components.composesupport.config.addComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
internal class CartComposeScreenTest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.simple().apply {
        addComposeSupport()
    }
) {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun checkClearButtonAppearance_whenCartIsEmpty_shouldNotBeDisplayed() {
        val emptyCartState = CartState.Content(cartItems = emptyList(), 0)

        testScreen(emptyCartState) {
            onNode(hasTestTag(CartScreenTestTags.CLEAR_CART_BUTTON))
                .assertIsNotDisplayed()
        }
    }
    @Test
    fun checkClearButtonAppearance_whenCartIsNotEmpty_shouldBeDisplayed() {
        val notEmptyCartState = CartState.Content(cartItems = getCartItems(), getCartSize())

        testScreen(notEmptyCartState) {
            onNode(hasTestTag(CartScreenTestTags.CLEAR_CART_BUTTON))
                .assertIsDisplayed()
        }
    }

    @Test
    fun checkCartSize() {
        val state = CartState.Content(cartItems = getCartItems(), getCartSize())

        val context = ApplicationProvider.getApplicationContext<Application>()
        val expectedTitle = context.getString(R.string.cart_size_title, state.cartSize)

        testScreen(state) {
            onNode(hasTestTag(CartScreenTestTags.CART_SIZE_TITLE))
                .assertIsDisplayed()
                .assertTextEquals(expectedTitle)
        }
    }

    @Test
    fun checkProductQuantities() {
        val state = CartState.Content(cartItems = getCartItems(), getCartSize())

        testScreen(state) {
            for(i in 0 until state.cartItems.size) {
                val cartItem = state.cartItems[i]
                onNode(
                    itemAt(i) and hasTestTag(CartScreenTestTags.PRODUCT_QUANTITY),
                    useUnmergedTree = true
                )
                    .assertIsDisplayed()
                    .assertTextEquals("x${cartItem.quantity}")
            }
        }
    }

    private fun testScreen(
        state: CartState,
        test: ComposeContentTestRule.() -> Unit
    ) {
        composeRule.setContent {
            CartScreenContent(
                onClickItem = { },
                onClearCart = { },
                state = state
            )
        }
        composeRule.test()
    }

    private fun itemAt(position: Int) : SemanticsMatcher =
        hasParent(
            hasTestTag(CartScreenTestTags.CART_ITEM)
                    and SemanticsMatcher.expectValue(LazyListItemPosition, position)
        )

    private fun getCartItems() : List<CartItemUiModel>
        = listOf(
        CartItemUiModel(
            product = ProductUiModel(
                id = 1,
                productTitle = "",
                priceInUSD = 1f,
                brand = ""
            ),
            quantity = 1
        ),
        CartItemUiModel(
            product = ProductUiModel(
                id = 2,
                productTitle = "",
                priceInUSD = 1f,
                brand = ""
            ),
            quantity = 2
        ),
        CartItemUiModel(
            product = ProductUiModel(
                id = 3,
                productTitle = "",
                priceInUSD = 1f,
                brand = ""
            ),
            quantity = 3
        ),
        CartItemUiModel(
            product = ProductUiModel(
                id = 4,
                productTitle = "",
                priceInUSD = 1f,
                brand = ""
            ),
            quantity = 4
        ),
        CartItemUiModel(
            product = ProductUiModel(
                id = 5,
                productTitle = "",
                priceInUSD = 1f,
                brand = ""
            ),
            quantity = 5
        ),
        CartItemUiModel(
            product = ProductUiModel(
                id = 6,
                productTitle = "",
                priceInUSD = 1f,
                brand = ""
            ),
            quantity = 6
        ),
        )

    private fun getCartSize() : Int {
        var cartSize = 0
        getCartItems().forEach { cartSize += it.quantity }
        return cartSize
    }
}