package com.cardes.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.cardes.data.db.CategoryDao
import com.cardes.data.db.FeeBeeDatabase
import com.cardes.data.db.SpendingDao
import com.cardes.data.db.entity.SpendingEntity
import com.cardes.data.db.entity.SpendingWithCategories
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal

@RunWith(AndroidJUnit4::class)
class FeeBeeDatabaseTest {
    private lateinit var db: FeeBeeDatabase
    private lateinit var spendingsDao: SpendingDao
    private lateinit var categoriesDao: CategoryDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room
            .inMemoryDatabaseBuilder(context, FeeBeeDatabase::class.java)
            .build()
        spendingsDao = db.spendingDao()
        categoriesDao = db.categoryDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun createAndReadCategories() {
        runBlocking {
            // Given
            val categories = TestUtil.createCategories(2)

            // When
            categoriesDao.createCategory(categories[0].copy(id = 0))
            categoriesDao.createCategory(categories[1].copy(id = 0))

            // Then
            assertThat(categoriesDao.getAllCategories()).isEqualTo(categories)
        }
    }

    @Test
    fun createSpendingWithCategories() {
        runBlocking {
            // Given
            val categories = TestUtil.createCategories(2)
            val spending = SpendingEntity(
                amount = BigDecimal("100.0"),
                content = "Gas",
                time = System.currentTimeMillis(),
            )

            // When
            categoriesDao.createCategory(categories[0])
            categoriesDao.createCategory(categories[1])
            spendingsDao.addSpendingWithCategories(spending, categories.map { categoryEntity -> categoryEntity.id })

            // Then
            val spendings = spendingsDao.getAllSpendings().first()
            assertThat(spendings).containsExactly(
                SpendingWithCategories(
                    spending = spending.copy(id = 1),
                    categories = categories,
                ),
            )
        }
    }
}
