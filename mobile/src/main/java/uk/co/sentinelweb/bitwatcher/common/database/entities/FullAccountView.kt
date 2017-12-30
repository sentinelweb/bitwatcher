package uk.co.sentinelweb.bitwatcher.common.database.entities

import android.arch.persistence.room.Relation
import uk.co.sentinelweb.domain.AccountType
import uk.co.sentinelweb.domain.ColourDomain

class FullAccountView {
    var id: Long = 0
    var name: String = ""
    var type: AccountType = AccountType.INITIAL
    var colour: ColourDomain = ColourDomain.TRANSPARENT

    @Relation(parentColumn = "id", entityColumn = "account_id", entity = PositionItemEntity::class)
    var balances: List<PositionItemEntity> = listOf()

    @Relation(parentColumn = "id", entityColumn = "account_id", entity = TradeEntity::class)
    var trades: List<TradeEntity> = listOf()

    @Relation(parentColumn = "id", entityColumn = "account_id", entity = TransactionEntity::class)
    var tranascations: List<TransactionEntity> = listOf()

    override fun toString(): String {
        return "fullaccount(${id} ${name} balances:${balances})"
    }
}