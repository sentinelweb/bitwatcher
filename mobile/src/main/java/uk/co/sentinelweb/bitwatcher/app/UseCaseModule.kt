package uk.co.sentinelweb.bitwatcher.app

import dagger.Binds
import dagger.Module
import dagger.Reusable
import uk.co.sentinelweb.bitwatcher.common.database.interactor.AccountInteractor
import uk.co.sentinelweb.bitwatcher.common.database.interactor.TickerRateInteractor
import uk.co.sentinelweb.bitwatcher.orchestrator.*
import uk.co.sentinelweb.use_case.*

@Module
interface UseCaseModule {

    @Binds
    @Reusable
    fun bindUpdateTickerUseCase(useCase: TickerDataOrchestrator): UpdateTickersUseCase

    @Binds
    @Reusable
    fun bindAccountRepositoryUseCase(useCase: AccountInteractor): AccountsRepositoryUseCase

    @Binds
    @Reusable
    fun bindBalanceUpdateUseCase(useCase: BalancesOrchestrator): BalanceUpdateUseCase

    @Binds
    @Reusable
    fun bindTickerUseCase(useCase: TickerRateInteractor): TickerUseCase


    @Binds
    @Reusable
    fun bindGetTransactionsUseCase(useCase: GetTransactionsOrchestrator): GetTransactionsUseCase

    @Binds
    @Reusable
    fun bindMarketDataUseCase(useCase: MarketDataInteractor): MarketsDataUseCase

    @Binds
    @Reusable
    fun bindTradeUseCase(useCase: TradeOrchestrator): TradeUseCase

}
