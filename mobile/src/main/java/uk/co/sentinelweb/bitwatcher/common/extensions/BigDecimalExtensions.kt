package uk.co.sentinelweb.bitwatcher.common.extensions

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.dp(scale: Int = 2): String = this.setScale(scale, RoundingMode.HALF_EVEN).toDouble().toString()
fun BigDecimal.div(divisor:BigDecimal): BigDecimal = this.divide(divisor,8, RoundingMode.HALF_EVEN)
