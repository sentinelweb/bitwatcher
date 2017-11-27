package uk.co.sentinelweb.bitwatcher.net

import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.net.bitstamp.BitstampService
import uk.co.sentinelweb.bitwatcher.net.coinfloor.CoinfloorService
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetModule {
    companion object {
        const val BITSTAMP = "Bitstamp"
        const val COINFLOOR = "CoinFloor"
    }

    @Provides
    @Singleton
    @Named(BITSTAMP)
    fun provideBitstampTickerDataApiInteractor(): TickerDataApiInteractor
            = TickerDataApiInteractor(BitstampService.Companion.GUEST)

    @Provides
    @Singleton
    @Named(COINFLOOR)
    fun provideCoinfloorTickerDataApiInteractor(): TickerDataApiInteractor
            = TickerDataApiInteractor(CoinfloorService.Companion.GUEST)
}