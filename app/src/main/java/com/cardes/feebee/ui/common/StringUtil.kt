package com.cardes.feebee.ui.common

import com.cardes.feebee.R
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

object StringUtil {
    fun parseBigDecimalString(bigDecimalString: String): BigDecimal {
        val decimalFormatSymbols = DecimalFormatSymbols().apply {
            groupingSeparator = ','
            decimalSeparator = '.'
        }
        val pattern = "#,##0.0#"
        val decimalFormat = DecimalFormat(pattern, decimalFormatSymbols).apply { isParseBigDecimal = true }
        return decimalFormat.parse(bigDecimalString) as BigDecimal
    }

    fun fromIntToMonthStringResourceId(monthIndex: Int) =
        when (monthIndex) {
            0 -> R.string.january_abbr
            1 -> R.string.february_abbr
            2 -> R.string.march_abbr
            3 -> R.string.april_abbr
            4 -> R.string.may_abbr
            5 -> R.string.june_abbr
            6 -> R.string.july_abbr
            7 -> R.string.august_abbr
            8 -> R.string.september_abbr
            9 -> R.string.october_abbr
            10 -> R.string.november_abbr
            11 -> R.string.december_abbr
            else -> R.string.empty
        }
}
