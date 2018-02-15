package uk.co.sentinelweb.bitwatcher.common.mapper

import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.domain.CurrencyCode
import java.math.BigDecimal
import javax.inject.Inject


class CurrencyFormatter @Inject constructor(val strings:StringMapper) {

    fun format(amount:BigDecimal, currency:CurrencyCode):String {
        return strings.getString(R.string.currency_format, amount.dp(3), currency.toString())
    }
}
