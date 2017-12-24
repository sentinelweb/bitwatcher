package uk.co.sentinelweb.domain.mappers

import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.CurrencyCode.*
import uk.co.sentinelweb.domain.CurrencyPair
import uk.co.sentinelweb.domain.TickerDomain
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
                    val key = getTickerKey(currency, base)
                    val reverseKey = getTickerKey(base, currency)
                    if (prices.containsKey(key)) {
                        total += balance.available * prices.get(key)?.last!!
                    } else if (prices.containsKey(reverseKey)) {
                        total += balance.available / prices.get(reverseKey)?.last!!
                    } else {
                        if (base.type == Type.FIAT) {// convert to BTC then convert back to target
                            val toCrypto = prices.get(getTickerKey(currency, BTC))
                            val toTarget = prices.get(getTickerKey(base, BTC))
                            if (toCrypto != null && toTarget != null) {
                                total += balance.available / toCrypto.last * toTarget.last
                            }
                        } else if (base.type == Type.CRYPTO) {
                            val toFiat = prices.get(getTickerKey(USD, currency))
                            val toTarget = prices.get(getTickerKey(USD, base))
                            if (toFiat != null && toTarget != null) {
                                total += balance.available * toFiat.last / toTarget.last
                            }
                        }
                    }
                }
            })
            return total
        }

        private fun getTickerKey(currency: CurrencyCode, base: CurrencyCode) = CurrencyPair.getKey(currency, base)
    }
}