package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list

class TransactionListPresenter(
        private val view: TransactionListContract.View
) : TransactionListContract.Presenter {
    override fun setInteractions(interactions: TransactionListContract.Interactions) {
        view.setInteractions(interactions)
    }

    override fun bindData(list: List<TransactionItemModel>) {
        view.setData(TransactionListState.TransactionItemListModel(list))
    }

}