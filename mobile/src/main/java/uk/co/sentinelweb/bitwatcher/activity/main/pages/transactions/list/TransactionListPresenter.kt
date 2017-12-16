package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list

import uk.co.sentinelweb.domain.TransactionItemDomain

class TransactionListPresenter(
        private val view: TransactionListContract.View
) : TransactionListContract.Presenter {
    override fun bindData(list: List<TransactionItemDomain>) {
        view.setData(TransactionListState.TransactionItemListModel(list))
    }
}