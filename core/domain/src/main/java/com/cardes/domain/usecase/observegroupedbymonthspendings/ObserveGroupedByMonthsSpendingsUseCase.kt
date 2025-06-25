package com.cardes.domain.usecase.observegroupedbymonthspendings

import com.cardes.domain.base.MonthYear
import com.cardes.domain.entity.Spending
import com.cardes.domain.repository.SpendingRepository
import kotlinx.coroutines.flow.Flow
import java.util.SortedMap
import javax.inject.Inject

interface ObserveGroupedByMonthsSpendingsUseCase {
    operator fun invoke(): Flow<SortedMap<MonthYear, SortedMap<Int, List<Spending>>>>
}

class ObserveGroupedByMonthsSpendingsUseCaseImpl @Inject constructor(
    private val spendingRepository: SpendingRepository,
) : ObserveGroupedByMonthsSpendingsUseCase {
    override fun invoke(): Flow<SortedMap<MonthYear, SortedMap<Int, List<Spending>>>> = spendingRepository.observeGroupedByMonthsSpending()
}
