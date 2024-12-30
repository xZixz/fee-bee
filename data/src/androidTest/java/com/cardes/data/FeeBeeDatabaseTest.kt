package com.cardes.data

import android.icu.util.Calendar
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
import kotlinx.coroutines.test.runTest
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
        db.clearAllTables()
        db.close()
    }

    @Test
    fun createAndReadCategories() {
        runBlocking {
            // Given
            val categories = TestUtil.createCategories(2)

            // When
            categoriesDao.createCategory(categories[0].copy(categoryId = 0))
            categoriesDao.createCategory(categories[1].copy(categoryId = 0))

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
            spendingsDao.addSpendingWithCategories(spending, categories.map { categoryEntity -> categoryEntity.categoryId })

            // Then
            val spendings = spendingsDao.observeAllSpendings().first()
            assertThat(spendings).containsExactly(
                SpendingWithCategories(
                    spending = spending.copy(id = 1),
                    categories = categories,
                ),
            )
        }
    }

    @Test
    fun testQuerySpendingsByCategories() =
        runTest {
            // Given
            val categories = TestUtil.createCategories(2)
            categoriesDao.createCategories(categories)
            (1..6).map { index ->
                val spending = TestUtil.createSpending(
                    id = index.toLong(),
                    content = "Spending num #$index",
                )
                val categoryId = when {
                    index <= 3 -> categories[0].categoryId
                    else -> categories[1].categoryId
                }
                spendingsDao.addSpendingWithCategories(spending, listOf(categoryId))
            }

            // When
            val spendingsWithCategory1 = spendingsDao.getSpendingsByCategoryIds(listOf(categories[0].categoryId))

            // Then
            assertThat(spendingsWithCategory1.map { spendingEntity -> spendingEntity.spending.id })
                .isEqualTo(listOf(1L, 2L, 3L))
        }

    @Test
    fun testQuerySpendingsByCategoriesAndByDateRange() =
        runTest {
            // Given
            val categories = TestUtil.createCategories(2)
            categoriesDao.createCategories(categories)

            val spendingOnMayCategory1 = TestUtil.createSpending(
                id = 1,
                content = "Spending on May",
                time = Calendar
                    .getInstance()
                    .apply {
                        set(Calendar.MONTH, 4)
                        set(Calendar.DAY_OF_MONTH, 1)
                    }.timeInMillis,
            )
            spendingsDao.addSpendingWithCategories(spendingOnMayCategory1, listOf(categories[0].categoryId))

            val spendingsOnJunWithCategory2 =
                (2..3).map { index ->
                    TestUtil.createSpending(
                        id = index.toLong(),
                        content = "Spending num #$index",
                        time = Calendar
                            .getInstance()
                            .apply {
                                set(Calendar.MONTH, 5)
                                set(Calendar.DAY_OF_MONTH, 1)
                            }.timeInMillis,
                    )
                }
            spendingsDao.addSpendingsWithCategories(
                spendingsOnJunWithCategory2.map { spending -> Pair(spending, listOf(categories[1].categoryId)) },
            )

            // When
            val aprilTime = TestUtil.timeOfFirstDayOfMonthInMilliseconds(4)
            val mayTime = TestUtil.timeOfFirstDayOfMonthInMilliseconds(5)
            val junTime = TestUtil.timeOfFirstDayOfMonthInMilliseconds(6)
            val julyTime = TestUtil.timeOfFirstDayOfMonthInMilliseconds(7)
            val spendingsOfMay = spendingsDao
                .getSpendingsByCategoryIdsByDateRange(
                    listOf(categories[0].categoryId),
                    from = aprilTime,
                    to = junTime,
                )
            assertThat(spendingsOfMay.map { it.spending.id }).isEqualTo(listOf(1L))

            val spendingsOfJun = spendingsDao
                .getSpendingsByCategoryIdsByDateRange(
                    listOf(categories[1].categoryId),
                    from = mayTime,
                    to = julyTime,
                )
            assertThat(spendingsOfJun.map { it.spending.id }).isEqualTo(listOf(2L, 3L))
        }

    @Test
    fun testQueryTotalSpentByCategoriesAndByDateRange() =
        runTest {
            // Given
            val categories = TestUtil.createCategories(2)
            categoriesDao.createCategories(categories)

            val spendingOnMayCategory1 = TestUtil.createSpending(
                id = 1,
                content = "Spending on May",
                time = Calendar
                    .getInstance()
                    .apply {
                        set(Calendar.MONTH, 4)
                        set(Calendar.DAY_OF_MONTH, 1)
                    }.timeInMillis,
            )
            spendingsDao.addSpendingWithCategories(spendingOnMayCategory1, listOf(categories[0].categoryId))

            val spendingsOnJunWithCategory2 =
                (2..3).map { index ->
                    TestUtil.createSpending(
                        id = index.toLong(),
                        content = "Spending num #$index",
                        time = Calendar
                            .getInstance()
                            .apply {
                                set(Calendar.MONTH, 5)
                                set(Calendar.DAY_OF_MONTH, 1)
                            }.timeInMillis,
                    )
                }
            spendingsDao.addSpendingsWithCategories(
                spendingsOnJunWithCategory2.map { spending -> Pair(spending, listOf(categories[1].categoryId)) },
            )

            val spendingOnJunWithCategory1 = TestUtil.createSpending(
                id = 100,
                content = "Spending more",
                time = Calendar
                    .getInstance()
                    .apply {
                        set(Calendar.MONTH, 5)
                        set(Calendar.DAY_OF_MONTH, 1)
                    }.timeInMillis,
                amount = BigDecimal(300),
            )
            spendingsDao.addSpendingWithCategories(spendingOnJunWithCategory1, listOf(categories[0].categoryId))

            // When
            val aprilTime = TestUtil.timeOfFirstDayOfMonthInMilliseconds(4)
            val mayTime = TestUtil.timeOfFirstDayOfMonthInMilliseconds(5)
            val junTime = TestUtil.timeOfFirstDayOfMonthInMilliseconds(6)
            val julyTime = TestUtil.timeOfFirstDayOfMonthInMilliseconds(7)
            val spendingsOfMay = spendingsDao
                .getSpendingsByCategoryIdsByDateRange(
                    listOf(categories[0].categoryId),
                    from = aprilTime,
                    to = junTime,
                )
            assertThat(spendingsOfMay.map { it.spending.id }).isEqualTo(listOf(1L))

            val totalSpent = spendingsDao
                .getTotalSpentOfCategoriesByDateRange(
                    from = mayTime,
                    to = julyTime,
                )
            assertThat(totalSpent).isEqualTo(
                mapOf(
                    categories[0].name to spendingOnMayCategory1.amount + spendingOnJunWithCategory1.amount,
                    categories[1].name to spendingsOnJunWithCategory2.sumOf { it.amount },
                ),
            )
        }
}
