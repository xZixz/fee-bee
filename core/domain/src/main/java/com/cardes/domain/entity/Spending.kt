package com.cardes.domain.entity

import java.math.BigDecimal

data class Spending(
    val id: Long,
    val time: Long,
    val content: String,
    val amount: BigDecimal,
    val categories: List<Category>,
)
