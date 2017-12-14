package uk.co.sentinelweb.bitwatcher.domain.mappers

import uk.co.sentinelweb.bitwatcher.domain.AccountDomain
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode
import uk.co.sentinelweb.bitwatcher.domain.CurrencyCode.*
import uk.co.sentinelweb.bitwatcher.domain.TickerDomain
import java.math.BigDecimal

class AccountTotalsMapper {
    companion object {

        // TODO this ned more work need to get ticker pairs for all conversions
        // this method does crypto-crypto/fiat-fiat byt converting to the other (fiat/crypto) and back again
        fun getTotal(domain: AccountDomain, base: CurrencyCode, prices: Map<String, TickerDomain>): BigDecimal {
            var total = BigDecimal.ZERO
            domain.balances.forEach({ balance ->
                val currency = balance.currency
                if (currency == base) {
                    total += balance.available
                } else {
                    val key = getTickerKey(base, currency)
                    val reverseKey = getTickerKey(currency, base)
                    if (prices.containsKey(key)) {
                        total += balance.available / prices.get(key)?.last!!
                    } else if (prices.containsKey(reverseKey)) {
                        total += balance.available * prices.get(reverseKey)?.last!!
                    } else {
                        if (base.type == Type.FIAT) {// convert to BTC then convert back to target
                            val toCrypto = prices.get(getTickerKey(BTC, currency))
                            val toTarget = prices.get(getTickerKey(BTC, base))
                            if (toCrypto != null && toTarget != null) {
                                total += balance.available / toCrypto.last * toTarget.last
                            }
                        } else if (base.type == Type.CRYPTO) {
                            val toFiat = prices.get(getTickerKey(currency, USD))
                            val toTarget = prices.get(getTickerKey(base, USD))
                            if (toFiat != null && toTarget != null) {
                                total += balance.available * toFiat.last / toTarget.last
                            }
                        }
                    }
//                    for (price in prices) {
//                        if (price.baseCurrencyCode == base && price.currencyCode == balance.currency) {
//                            total += balance.available * price.last
//                            break
//                        } else if (price.currencyCode == base && price.baseCurrencyCode == balance.currency) {
//                            total += balance.available.setScale(6) / price.last
//                            break
//                        }
//                    }
                }
            })
            return total
        }

        private fun getTickerKey(base: CurrencyCode, currency: CurrencyCode) = base.toString() + "." + currency.toString()
    }
}