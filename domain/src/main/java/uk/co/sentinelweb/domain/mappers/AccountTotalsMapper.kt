package uk.co.sentinelweb.domain.mappers

import uk.co.sentinelweb.domain.*
import uk.co.sentinelweb.domain.CurrencyCode.*
import java.math.BigDecimal

class AccountTotalsMapper {
    companion object {

        // TODO this need more work need to get ticker pairs for all conversions
        // this method does crypto-crypto/fiat-fiat byt converting to the other (fiat/crypto) and back again
        fun getTotal(domain: AccountDomain, base: CurrencyCode, prices: Map<String, TickerDomain>): BigDecimal {
            val mapper = AccountTotalsMapper()
            var total = BigDecimal.ZERO
            domain.balances.forEach({ balance ->
                total += mapper.calculateHoldingInBaseCurrency(balance, base, prices)
            })
            return total
        }

        private fun getTickerKey(currency: CurrencyCode, base: CurrencyCode) = CurrencyPair.getKey(currency, base)
    }

    internal fun calculateHoldingInBaseCurrency(balance: BalanceDomain, base: CurrencyCode, prices: Map<String, TickerDomain>): BigDecimal {
        val currency = balance.currency
        var holding = BigDecimal.ZERO
        if (currency == base) {
            holding = balance.available
        } else {
            val key = getTickerKey(currency, base)
            val reverseKey = getTickerKey(base, currency)
            if (prices.containsKey(key)) {
                holding = balance.available * prices.get(key)?.last!!
            } else if (prices.containsKey(reverseKey)) {
                holding = balance.available / prices.get(reverseKey)?.last!!
            } else {
                if (base.type == Type.FIAT) {// convert to BTC then convert back to target
                    val toCrypto = getPrice(currency, BTC, prices)
                    val toTarget = getPrice(base, BTC, prices)
                    if (toCrypto != null && toTarget != null) {
                        holding = balance.available * toTarget.divide(toCrypto, mc)
                    }
                } else if (base.type == Type.CRYPTO) {
                    val toFiat = getPrice(USD, currency, prices)
                    val toTarget = getPrice(USD, base, prices)
                    if (toFiat != null && toTarget != null) {
                        holding = balance.available * toTarget.divide(toFiat, mc)
                    }
                }
            }
        }
        return holding
    }

    private fun getPrice(currency: CurrencyCode, base: CurrencyCode, prices: Map<String, TickerDomain>): BigDecimal? {
        if (currency == base) {
            return BigDecimal.ONE
        } else if (prices.containsKey(getTickerKey(currency, base))) {
            return prices.get(getTickerKey(currency, base))?.last
        } else if (prices.containsKey(getTickerKey(base, currency))) {
            return BigDecimal.ONE.divide(prices.get(getTickerKey(base, currency))?.last, mc)
        }
        return null
    }
}