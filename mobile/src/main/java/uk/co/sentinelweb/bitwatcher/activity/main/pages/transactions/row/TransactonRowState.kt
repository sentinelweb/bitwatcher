package uk.co.sentinelweb.bitwatcher.activity.main.pages.transactions.row

import uk.co.sentinelweb.domain.TransactionItem

data class TransactonRowState constructor(var transaction:TransactionItem) {

    data class TransactionRowDisplayModel constructor(var amount:String, var date:String) {

    }
}