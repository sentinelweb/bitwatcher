package uk.co.sentinelweb.bitwatcher.net

import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import uk.co.sentinelweb.bitwatcher.BuildConfig
import uk.co.sentinelweb.bitwatcher.net.interactor.TickerMergeInteractor
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeDataProvider
import uk.co.sentinelweb.bitwatcher.net.xchange.ExchangeService
import uk.co.sentinelweb.bitwatcher.net.xchange.binance.BinanceTickerDataApiInteractor
import uk.co.sentinelweb.bitwatcher.net.xchange.bitstamp.BalanceApiInteractor
import uk.co.sentinelweb.bitwatcher.net.xchange.bitstamp.BitstampService
import uk.co.sentinelweb.bitwatcher.net.xchange.bitstamp.BitstampTradeHistoryParamsProvider
import uk.co.sentinelweb.bitwatcher.net.xchange.coinfloor.CoinfloorService
import uk.co.sentinelweb.bitwatcher.net.xchange.gdax.GdaxService
import uk.co.sentinelweb.bitwatcher.net.xchange.generic.TickerDataApiInteractor
import uk.co.sentinelweb.bitwatcher.net.xchange.generic.TradeApiInteractor
import uk.co.sentinelweb.bitwatcher.net.xchange.generic.TransactionApiInteractor
import uk.co.sentinelweb.bitwatcher.net.xchange.kraken.KrakenService
import uk.co.sentinelweb.bitwatcher.net.xchange.mapper.TradeMapper
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
        const val BINANCE = "Binance"
    }

    @Provides
    @Reusable
    @Named(BITSTAMP)
    fun provideBitstampTickerDataApiInteractor(): TickerDataInteractor
            = TickerDataApiInteractor(BitstampService.GUEST)

    @Provides
    @Reusable
    @Named(COINFLOOR)
    fun provideCoinfloorTickerDataApiInteractor(): TickerDataInteractor
            = TickerDataApiInteractor(CoinfloorService.GUEST)

    @Provides
    @Reusable
    @Named(KRAKEN)
    fun provideKrakenTickerDataApiInteractor(): Observable<TickerDataInteractor>
            = BehaviorSubject.fromCallable(object : Callable<TickerDataInteractor> {
        override fun call(): TickerDataApiInteractor {
            return TickerDataApiInteractor(KrakenService.GUEST)
        }
    })

    @Provides
    @Reusable
    @Named(BINANCE)
    fun provideBinanceTickerDataApiInteractor(): TickerDataInteractor
            = BinanceTickerDataApiInteractor()


    @Provides
    @Named(GDAX)
    @Singleton
    fun provideGdaxTickerDataApiInteractor(): Observable<TickerDataInteractor>
            = BehaviorSubject.fromCallable(object : Callable<TickerDataInteractor> {
        override fun call(): TickerDataInteractor {
            return TickerDataApiInteractor(GdaxService.GUEST)
        }
    })

    @Provides
    @Named(BITSTAMP)
    fun provideBitstampService(): ExchangeService {
        val dataProvider = ExchangeDataProvider(
                BuildConfig.bitstampApiKey,
                BuildConfig.bitstampSecretKey,
                BuildConfig.bitstampUser)
        return BitstampService(dataProvider)
    }

    @Provides
    @Named(BITSTAMP)
    fun provideBitstampBalanceApiInteractor(@Named(BITSTAMP) service: ExchangeService): BalanceApiInteractor {
        return BalanceApiInteractor(service)
    }

    @Provides
    @Named(BITSTAMP)
    fun provideBitstampTradesInteractor(@Named(BITSTAMP) service: ExchangeService): TradeDataInteractor {
        return TradeApiInteractor(service, TradeMapper(), BitstampTradeHistoryParamsProvider())
    }

    @Provides
    @Named(BITSTAMP)
    fun provideBitstampTransactionsInteractor(@Named(BITSTAMP) service: ExchangeService): TransactionsDataInteractor {
        return TransactionApiInteractor(service)
    }

    @Provides
    @Singleton
    fun provideMergedInteractor(@Named(BITSTAMP) tickerBitstampInteractor: TickerDataInteractor,
                                @Named(COINFLOOR) tickerCoinfloorInteractor: TickerDataInteractor,
                                @Named(BINANCE) tickerBinanceInteractor: TickerDataInteractor,
                                @Named(KRAKEN) tickerKrakenInteractor: Observable<TickerDataInteractor>
    ): TickerMergeInteractor
            = TickerMergeInteractor(tickerBitstampInteractor, tickerCoinfloorInteractor, tickerKrakenInteractor, tickerBinanceInteractor)


}