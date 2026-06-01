package com.example.productsStore.testing.data.mapper

import com.example.productsStore.data.dto.ProductDetailsApi
import com.example.productsStore.data.mapper.toDomain
import com.example.productsStore.data.mapper.toEntity
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class DataMappersTest {    // тестируем только те мапперы, где есть хоть какая-то логика
    @Test
    fun `GIVEN imageUrls not null WHEN toDomain THEN returns state with first url`() {
        // GIVEN
        val productDetailsApi = ProductDetailsApi(
            title = "",
            description = "",
            imageUrls = listOf("firstUrl", "secondUrl"),
            rating = 1f,
            price = 1f,
            weight = 1,
            availabilityStatus = "",
            warrantyInformation = ""
        )
        // WHEN
        val actual = productDetailsApi.toDomain()
        // THEN
        val expectedImageUrl = "firstUrl"

        assertEquals(expectedImageUrl, actual.imageUrl)
    }
    @Test
    fun `GIVEN imageUrls not null WHEN toEntity THEN returns state with first url`() {
        // GIVEN
        val productDetailsApi = ProductDetailsApi(
            title = "",
            description = "",
            imageUrls = listOf("firstUrl", "secondUrl"),
            rating = 1f,
            price = 1f,
            weight = 1,
            availabilityStatus = "",
            warrantyInformation = ""
        )
        // WHEN
        val actual = productDetailsApi.toEntity(1)
        // THEN
        val expectedImageUrl = "firstUrl"

        assertEquals(expectedImageUrl, actual.imageUrl)
    }
}