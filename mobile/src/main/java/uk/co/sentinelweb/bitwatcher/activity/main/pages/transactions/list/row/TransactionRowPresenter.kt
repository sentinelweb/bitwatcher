package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row

import uk.co.sentinelweb.domain.TransactionItemDomain

class TransactionRowPresenter(
       private val view: TransactionRowContract.View,
       private val mapper:TransactionRowDisplayMapper = TransactionRowDisplayMapper()) : TransactionRowContract.Presenter {
    val state = TransactonRowState()

    override fun bindData(domain: TransactionItemDomain) {
        val model = mapper.map(domain)
        state.model = model
        view.setData(model)
    }
}