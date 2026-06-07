package com.cardes.domain.usecase.observegroupedbymonthspendings

import com.cardes.domain.base.MonthYear
import com.cardes.domain.entity.Spending
import kotlinx.coroutines.flow.Flow
import java.util.SortedMap

fun interface ObserveGroupedByMonthsSpendingsUseCase {
    operator fun invoke(): Flow<SortedMap<MonthYear, SortedMap<Int, List<Spending>>>>
}
