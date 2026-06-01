package com.example.productsStore.testing.data.repository

import com.example.productsStore.core.Resource
import com.example.productsStore.data.dto.ProductDetailsApi
import com.example.productsStore.data.local.entity.ProductDetailsEntity
import com.example.productsStore.data.repository.ProductsRepositoryImpl
import com.example.productsStore.testing.stub.LoggingProviderStub
import com.example.productsStore.testing.stub.ProductDetailsCacheDaoStub
import com.example.productsStore.testing.stub.ProductsServiceStub
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Clock

internal class ProductRepositoryImplTest {

    private lateinit var loggingProvider: LoggingProviderStub
    private lateinit var productDetailsCacheDao: ProductDetailsCacheDaoStub
    private lateinit var productsService: ProductsServiceStub

    @BeforeEach
    fun setUp() {
        loggingProvider = LoggingProviderStub()
        productDetailsCacheDao = ProductDetailsCacheDaoStub()
        productsService = ProductsServiceStub()
    }

    @Test
    fun `GIVEN actual cached data WHEN getProductDetails THEN cache is taken, no network request`() = runTest {
        // GIVEN
        val expectedTitle = "Fresh details from cache"

        val cachedDetails = ProductDetailsEntity(
            id = 1,
            title = expectedTitle,
            description = "",
            rating = 1f,
            price = 1f,
            weight = 1,
            availabilityStatus = "",
            imageUrl = "imageUrl",
            warrantyInformation = "",
            timestamp = Clock.System.now().epochSeconds, // кэш точно не просрочен
        )
        val detailsFromNetwork = ProductDetailsApi(
            title = "Fresh details from network",
            description = "",
            imageUrls = listOf("firstUrl", "secondUrl"),
            rating = 1f,
            price = 1f,
            weight = 1,
            availabilityStatus = "",
            warrantyInformation = ""
        )
        // WHEN
        productsService.productDetailsToBeReturned = detailsFromNetwork
        productDetailsCacheDao.productDetailsToBeReturned = cachedDetails
        val actual = createRepository().getProductDetails(1, "")

        // THEN
        assertTrue(actual is Resource.Success)
        assertEquals(expectedTitle, actual.data.title)
    }

    @Test
    fun `GIVEN not actual cached data WHEN getProductDetails THEN network request, cache is updating`() = runTest {
        // GIVEN
        val expectedTitle = "Fresh details from network"

        val cachedDetails = ProductDetailsEntity(
            id = 1,
            title = "Cached details",
            description = "",
            rating = 1f,
            price = 1f,
            weight = 1,
            availabilityStatus = "",
            imageUrl = "imageUrl",
            warrantyInformation = "",
            timestamp = 1L, // кэш точно просрочен
        )
        val detailsFromNetwork = ProductDetailsApi(
            title = expectedTitle,
            description = "",
            imageUrls = listOf("firstUrl", "secondUrl"),
            rating = 1f,
            price = 1f,
            weight = 1,
            availabilityStatus = "",
            warrantyInformation = ""
        )

        // WHEN
        productsService.productDetailsToBeReturned = detailsFromNetwork
        productDetailsCacheDao.productDetailsToBeReturned = cachedDetails
        val actual = createRepository().getProductDetails(1, "")
        val newCache = productDetailsCacheDao.savedProductDetails

        // THEN
        assertTrue(actual is Resource.Success)

        assertEquals(expectedTitle, actual.data.title)
        assertNotNull(newCache)   // тут проверка, что кэш обновился
        assertEquals(expectedTitle, newCache.title)
    }

    @Test
    fun `GIVEN no cache for query WHEN getProductDetails THEN network request`() = runTest {
        // GIVEN
        val expectedTitle = "Fresh details from network"

        val cachedDetails = null
        val detailsFromNetwork = ProductDetailsApi(
            title = expectedTitle,
            description = "",
            imageUrls = listOf("firstUrl", "secondUrl"),
            rating = 1f,
            price = 1f,
            weight = 1,
            availabilityStatus = "",
            warrantyInformation = ""
        )
        // WHEN
        productsService.productDetailsToBeReturned = detailsFromNetwork
        productDetailsCacheDao.productDetailsToBeReturned = cachedDetails
        val actual = createRepository().getProductDetails(1, "")

        // THEN
        assertTrue(actual is Resource.Success)
        assertEquals(expectedTitle, actual.data.title)
    }

    @Test
    fun `GIVEN network error and expired cache WHEN getProductsDetails THEN return cached data`() = runTest {
        // GIVEN
        val expectedTitle = "Old details from cache"
        val cachedDetails = ProductDetailsEntity(
            id = 1,
            title = expectedTitle,
            description = "",
            rating = 1f,
            price = 1f,
            weight = 1,
            availabilityStatus = "",
            imageUrl = "imageUrl",
            warrantyInformation = "",
            timestamp = 1L, // кэш точно просрочен
        )
        val shouldServiceThrowException = true

        // WHEN
        productsService.shouldThrowException = shouldServiceThrowException
        productDetailsCacheDao.productDetailsToBeReturned = cachedDetails
        val actual = createRepository().getProductDetails(1, "")

        // THEN
        assertTrue(actual is Resource.Success)
        assertEquals(expectedTitle, actual.data.title)
    }

    @Test
    fun `GIVEN network error and no cache WHEN getProductsDetails THEN return correct answer`() = runTest {
        // GIVEN
        val shouldServiceThrowException = true
        val cachedDetails = null

        // WHEN
        productsService.shouldThrowException = shouldServiceThrowException
        productDetailsCacheDao.productDetailsToBeReturned = cachedDetails
        val actual = createRepository().getProductDetails(1, "")

        // THEN
        assertTrue(actual is Resource.Error) // если это Resource.Error, то там DomainError, а значит результат точно будет обработан в presentation
    }

    @Test
    fun `GIVEN success network answer WHEN getProductsDetails THEN cache is updating with correct timestamp`() = runTest {
        // GIVEN
        val permissibleDeviation = 60L      // Если метка времени не старше 60 секунд (вдруг долго будет проходить тест), то она свежая
        // WHEN
        createRepository().getProductDetails(1, "")
        val newDetails = productDetailsCacheDao.savedProductDetails

        // THEN
        assertNotNull(newDetails)
        assertTrue(newDetails.timestamp + permissibleDeviation > Clock.System.now().epochSeconds)
    }

    private fun createRepository() = ProductsRepositoryImpl(productsService, productDetailsCacheDao, loggingProvider)
}