package uk.co.sentinelweb.bitwatcher.net.xchange

import org.knowm.xchange.Exchange


abstract class ExchangeProvider() {

    val userExchange: Exchange
        get() = createExchange()

    val guestExchange: Exchange
        get() = createGuestExchange()

    abstract fun createExchange():Exchange
    abstract fun createGuestExchange():Exchange
}