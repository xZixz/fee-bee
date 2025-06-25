package com.cardes.designsystem.common

import java.text.SimpleDateFormat
import java.util.Locale

val nonBigDecimalCharRegex = "[^0-9,.]".toRegex()
val spendingDateDisplayFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.US)
val monthDayYearDisplayFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
val monthYearDisplayFormat = SimpleDateFormat("MMMM yyyy", Locale.US)
