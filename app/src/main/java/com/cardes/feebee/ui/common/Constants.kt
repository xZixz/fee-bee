package com.cardes.feebee.ui.common

import java.text.SimpleDateFormat
import java.util.Locale

val nonBigDecimalCharRegex = "[^0-9,.]".toRegex()
val spendingDateDisplayFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.US)
val monthDayYearDisplayFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
