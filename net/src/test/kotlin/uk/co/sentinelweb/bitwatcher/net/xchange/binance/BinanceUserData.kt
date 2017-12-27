package uk.co.sentinelweb.bitwatcher.net.xchange.binance

class BinanceUserData {

    companion object {
        val key = System.getProperty("BINANCE_API_KEY")
        val secret = System.getProperty("BINANCE_SECRET")
        val user = System.getProperty("BINANCE_USER")

    }

}