package uk.co.sentinelweb.bitwatcher.common.extensions

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.dp(scale: Int = 2): String = this.setScale(scale, RoundingMode.HALF_EVEN).toDouble().toString()
