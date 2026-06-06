package com.example.productsStore.testing.ui.test

import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.core.net.toUri
import com.example.productsStore.presentation.contract.ProductDetailsScreenState
import com.example.productsStore.presentation.contract.ProductDetailsState
import com.example.productsStore.presentation.model.ProductDetailsUiModel
import com.example.productsStore.presentation.ui.screen.ProductDetailsScreenContent
import com.example.productsStore.presentation.ui.screen.testing.testtags.ProductDetailsTestTags
import com.kaspersky.components.composesupport.config.addComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
internal class ProductDetailsComposeScreenTest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.simple().apply {
        addComposeSupport()
    }
) {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun checkImageAppearance_whenUriIsNull_shouldNotBeDisplayed() {
        val state = ProductDetailsState(
            productId = 1,
            screenState = ProductDetailsScreenState.Content(
                getDetails().copy(mainImageUri = null)
            )
        )

        testScreen(state) {
            onNode(hasTestTag(ProductDetailsTestTags.PRODUCT_IMAGE))
                .assertIsNotDisplayed()
        }
    }

    @Test
    fun checkImageAppearance_whenUriIsNotNull_shouldBeDisplayed() {
        val state = ProductDetailsState(
            productId = 1,
            screenState = ProductDetailsScreenState.Content(
                getDetails().copy(mainImageUri = "imageUri".toUri())
            )
        )

        testScreen(state) {
            onNode(hasTestTag(ProductDetailsTestTags.PRODUCT_IMAGE))
                .assertIsDisplayed()
        }
    }

    private fun testScreen(
        state: ProductDetailsState,
        test: ComposeContentTestRule.() -> Unit
    ) {
        composeRule.setContent {
            ProductDetailsScreenContent(
                state = state,
                scrollState = rememberScrollState(),
                onRefreshDetails = {  }
            )
        }
        composeRule.test()
    }

    private fun getDetails() : ProductDetailsUiModel =
        ProductDetailsUiModel(
            productTitle = "",
            description = "",
            mainImageUri = null,
            rating = 1f,
            priceInUSD = 1f,
            weight = 1,
            availabilityStatus = "",
            warrantyInformation = "",
            isExpiredInfo = false
        )
}