package uk.co.sentinelweb.bitwatcher.net

import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.net.bitstamp.BitstampService
import uk.co.sentinelweb.bitwatcher.net.coinfloor.CoinfloorService
import uk.co.sentinelweb.bitwatcher.net.gdax.GdaxService
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetModule {
    companion object {
        const val BITSTAMP = "Bitstamp"
        const val COINFLOOR = "CoinFloor"
        //const val GDAX = "GDAX"
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

//    @Provides
//    @Singleton
//    @Named(GDAX)
//    fun provideGdaxTickerDataApiInteractor(): TickerDataApiInteractor
//            = TickerDataApiInteractor(GdaxService.Companion.GUEST)
}