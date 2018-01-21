package uk.co.sentinelweb.bitwatcher.common.ui.transaction_list.row

import uk.co.sentinelweb.bitwatcher.common.ui.transaction_list.TransactionItemModel

class TransactionRowPresenter(
        private val view: TransactionRowContract.View,
        private val interactions: TransactionRowContract.Interactions,
        private val mapper: TransactionRowDisplayMapper = TransactionRowDisplayMapper()) : TransactionRowContract.Presenter {
    val state = TransactonRowState()

    init {
        view.setPresenter(this)
    }

    override fun bindData(model: TransactionItemModel, selected: Boolean) {
        state.transaction = model
        state.selected = selected
        updateView()
    }

    override fun onSelect() {
        state.selected = !state.selected
        state.transaction?.let { transaction -> interactions.onSelect(transaction) }
        updateView()
    }

    private fun updateView() {
        val displayModel = mapper.map(state.transaction!!, state.selected)
        view.setData(displayModel)
    }
}