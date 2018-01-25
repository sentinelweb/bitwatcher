package uk.co.sentinelweb.bitwatcher.common.database.mapper

import uk.co.sentinelweb.bitwatcher.common.database.entities.TradeEntity
import uk.co.sentinelweb.domain.TransactionItemDomain
import javax.inject.Inject

class TradeEntityToDomainMapper @Inject constructor() {

    /**
     * Map trade domain
     */
    fun map(input:TradeEntity):TransactionItemDomain.TradeDomain {
        return TransactionItemDomain.TradeDomain(
                input.tid,
                input.date,
                input.amount,
                input.currencyCodeFrom,
                input.type,
                input.price,
                input.currencyCodeTo,
                input.feesAmount,
                input.feesCurrency,
                input.status
        )
    }

    fun mapList(entityList: List<TradeEntity>): List<TransactionItemDomain.TradeDomain> {
        return entityList.flatMap { t -> listOf(map(t)) }.toList() // TODO intermediate list creation
    }
}