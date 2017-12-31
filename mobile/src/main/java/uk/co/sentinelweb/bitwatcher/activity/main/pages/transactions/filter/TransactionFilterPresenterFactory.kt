package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter

import uk.co.sentinelweb.bitwatcher.common.database.interactor.AccountInteractor
import uk.co.sentinelweb.bitwatcher.common.preference.TransactionFilterInteractor
import javax.inject.Inject

class TransactionFilterPresenterFactory @Inject constructor(
        private val accountDbInteractor:AccountInteractor,
        private val preferences: TransactionFilterInteractor
){

    fun createPresenter(view:TransactionFilterContract.View): TransactionFilterContract.Presenter {
        val transactionFilterPresenter = TransactionFilterPresenter(
                view = view,
                accountsRepositoryUseCase =  accountDbInteractor,
                preferences = preferences)
        view.setFilterPresenter(transactionFilterPresenter)
        return transactionFilterPresenter
    }

}
