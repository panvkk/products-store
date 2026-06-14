package com.example.productsStore.testing.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.productsStore.data.local.ProductsStoreDatabase
import com.example.productsStore.data.local.dao.ProductCartDao
import com.example.productsStore.data.mapper.toCartEntity
import com.example.productsStore.data.repository.CartRepositoryImpl
import com.example.productsStore.domain.model.Product
import com.example.productsStore.testing.stub.LoggerStub
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
internal class CartRepositoryImplTest {
    private lateinit var logger: LoggerStub

    private lateinit var db: ProductsStoreDatabase
    private lateinit var productsCartDao: ProductCartDao

    @Before
    fun setup() {
        logger = LoggerStub()
        createDb()
    }

    private fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, ProductsStoreDatabase::class.java).build()
        productsCartDao = db.productCartDao()
    }

    @After
    fun closeDb() { db.close() }

    @Test
    fun GIVEN_productIsFirstTimeInCart_WHEN_addToCart_THEN_newRecordInDb() = runTest {
        // GIVEN
        val product = Product(id = 1337, title = "", price = 1f, brand = "")

        // WHEN
        createRepository().addToCart(product)

        // THEN
        assertEquals(1, productsCartDao.getProductQuantityInCart(product.id))
    }

    @Test
    fun GIVEN_productIsNotFirstTimeInCart_WHEN_addToCart_THEN_noNewRecordInDb() = runTest {
        // GIVEN
        val product = Product(id = 1337, title = "", price = 1f, brand = "")
        productsCartDao.putProduct(product.toCartEntity(1, false))

        // WHEN
        createRepository().addToCart(product)

        // THEN
        assertEquals(1, productsCartDao.getCartedProducts().first().size)
        assertEquals(2, productsCartDao.getProductQuantityInCart(product.id))
    }

    @Test
    fun GIVEN_severalProducts_WHEN_addToCartEach_THEN_AllAdded() = runTest {
        // GIVEN
        val product = Product(id = 1337, title = "", price = 1f, brand = "")
        val productIds = listOf(1, 2, 3, 4, 5, 6, 7, 8)

        // WHEN
        productIds.forEach { createRepository().addToCart(product.copy(id = it)) }
        val actualCount = productsCartDao.getCartedProducts().first().size

        // THEN
        val expectedCount = 8
        assertEquals(expectedCount, actualCount)
    }

    @Test
    fun GIVEN_severalProducts_WHEN_clearCart_THEN_AllDeleted() = runTest {
        // GIVEN
        val product = Product(id = 1337, title = "", price = 1f, brand = "")
        val productIds = listOf(1, 2, 3, 4, 5, 6, 7, 8)
        productIds.forEach { productsCartDao.putProduct(
            product.copy(id = it).toCartEntity(1, false)
        )
        }

        // WHEN
        createRepository().clearCart()

        val actualCount = productsCartDao.getCartedProducts().first().size

        // THEN
        val expectedCount = 0
        assertEquals(expectedCount, actualCount)
    }
    private fun createRepository() = CartRepositoryImpl(productsCartDao, logger)
}