package com.cardes.feebee.ui.common

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
}
