package uk.co.sentinelweb.bitwatcher.domain

enum class AccountType {
    INITIAL, BITSTAMP, KRAKEN, BINANCE, GHOST, WALLET;
    companion object {
        private const val serialVersionUID: Long = 4563645846293123423
    }
}