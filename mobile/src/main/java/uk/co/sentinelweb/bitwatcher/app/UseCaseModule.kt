package uk.co.sentinelweb.bitwatcher.app

import dagger.Binds
import dagger.Module
import dagger.Reusable
import uk.co.sentinelweb.bitwatcher.common.database.interactor.AccountInteractor
import uk.co.sentinelweb.bitwatcher.common.database.interactor.TickerRateInteractor
import uk.co.sentinelweb.bitwatcher.orchestrator.BalancesOrchestrator
import uk.co.sentinelweb.bitwatcher.orchestrator.TickerDataOrchestrator
import uk.co.sentinelweb.use_case.AccountsRepositoryUseCase
import uk.co.sentinelweb.use_case.BalanceUpdateUseCase
import uk.co.sentinelweb.use_case.TickerUseCase
import uk.co.sentinelweb.use_case.UpdateTickersUseCase

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


}
