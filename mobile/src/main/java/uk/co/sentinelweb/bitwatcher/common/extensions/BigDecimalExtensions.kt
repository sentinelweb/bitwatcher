package uk.co.sentinelweb.bitwatcher.common.extensions

import uk.co.sentinelweb.domain.mc
import java.math.BigDecimal

fun BigDecimal.dp(scale: Int = 2): String = this.setScale(scale, mc.roundingMode).toDouble().toString()
fun BigDecimal.div(divisor:BigDecimal): BigDecimal = this.divide(divisor, mc)
