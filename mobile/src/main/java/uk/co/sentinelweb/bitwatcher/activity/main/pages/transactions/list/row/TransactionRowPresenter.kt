package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row

import uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.TransactionItemModel

class TransactionRowPresenter(
       private val view: TransactionRowContract.View,
       private val mapper:TransactionRowDisplayMapper = TransactionRowDisplayMapper()) : TransactionRowContract.Presenter {
    val state = TransactonRowState()

    override fun bindData(model: TransactionItemModel) {
        val displayModel = mapper.map(model)
        state.model = displayModel
        view.setData(displayModel)
    }
}