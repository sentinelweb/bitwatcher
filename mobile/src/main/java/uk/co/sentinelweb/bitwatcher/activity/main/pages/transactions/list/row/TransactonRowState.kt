package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.list.row

import uk.co.sentinelweb.domain.TransactionItemDomain

data class TransactonRowState constructor(var transaction: TransactionItemDomain) {

    data class TransactionRowDisplayModel constructor(var amount:String, var date:String) {

    }
}