package uk.co.sentinelweb.bitwatcher.net

import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import uk.co.sentinelweb.bitwatcher.BuildConfig
import uk.co.sentinelweb.bitwatcher.net.bitstamp.BitstampService
import uk.co.sentinelweb.bitwatcher.net.coinfloor.CoinfloorService
import uk.co.sentinelweb.bitwatcher.net.gdax.GdaxService
import uk.co.sentinelweb.bitwatcher.net.interactor.TickerMergeInteractor
import uk.co.sentinelweb.bitwatcher.net.kraken.KrakenService
import java.util.concurrent.Callable
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetModule {
    companion object {
        const val BITSTAMP = "Bitstamp"
        const val COINFLOOR = "CoinFloor"
        const val GDAX = "GDAX"
        const val KRAKEN = "Kraken"
    }

    @Provides
    @Reusable
    @Named(BITSTAMP)
    fun provideBitstampTickerDataApiInteractor(): TickerDataApiInteractor
            = TickerDataApiInteractor(BitstampService.Companion.GUEST)

    @Provides
    @Reusable
    @Named(COINFLOOR)
    fun provideCoinfloorTickerDataApiInteractor(): TickerDataApiInteractor
            = TickerDataApiInteractor(CoinfloorService.Companion.GUEST)

    @Provides
    @Reusable
    @Named(KRAKEN)
    fun provideKrakenTickerDataApiInteractor():Observable<TickerDataApiInteractor>
            = BehaviorSubject.fromCallable(  object : Callable<TickerDataApiInteractor> {
        override fun call(): TickerDataApiInteractor {
            return TickerDataApiInteractor(KrakenService.Companion.GUEST)
        }
    })

    @Provides
    @Named(GDAX)
    @Singleton
    fun provideGdaxTickerDataApiInteractor(): Observable<TickerDataApiInteractor>
            = BehaviorSubject.fromCallable(  object : Callable<TickerDataApiInteractor> {
            override fun call(): TickerDataApiInteractor {
                return TickerDataApiInteractor(GdaxService.Companion.GUEST)
            }
        })

    @Provides
    @Reusable
    @Named(BITSTAMP)
    fun provideBitstampBalanceApiInteractor(): BalanceApiInteractor {
        val dataProvider = ExchangeDataProvider(
                BuildConfig.balancesApiKey,
                BuildConfig.balancesSecretKey,
                BuildConfig.balancesUser)
        return BalanceApiInteractor(BitstampService(dataProvider))
    }


    @Provides
    @Singleton
    fun provideMergedInteractor(@Named(BITSTAMP) tickerBitstampInteractor: TickerDataApiInteractor,
                                @Named(COINFLOOR) tickerCoinfloorInteractor: TickerDataApiInteractor,
                                @Named(KRAKEN) tickerKrakenInteractor: Observable<TickerDataApiInteractor>): TickerMergeInteractor
            = TickerMergeInteractor(tickerBitstampInteractor, tickerCoinfloorInteractor, tickerKrakenInteractor)


}