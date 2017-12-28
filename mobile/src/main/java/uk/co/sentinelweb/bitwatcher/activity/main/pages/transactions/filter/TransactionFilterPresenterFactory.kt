package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.filter

import uk.co.sentinelweb.bitwatcher.common.database.interactor.AccountInteractor
import javax.inject.Inject

class TransactionFilterPresenterFactory @Inject constructor(
        val accountDbInteractor:AccountInteractor
){

    fun createPresenter(view:TransactionFilterContract.View): TransactionFilterContract.Presenter {
        val transactionFilterPresenter = TransactionFilterPresenter(view = view, accountsRepositoryUseCase =  accountDbInteractor)
        view.setFilterPresenter(transactionFilterPresenter)
        return transactionFilterPresenter
    }

}
