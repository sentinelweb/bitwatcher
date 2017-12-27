package uk.co.sentinelweb.bitwatcher.net.xchange.bitstamp

class BitstampUserData {

    companion object {
        val key = System.getProperty("BITSTAMP_API_KEY")
        val secret = System.getProperty("BITSTAMP_SECRET")
        val user = System.getProperty("BITSTAMP_USER")

    }

}