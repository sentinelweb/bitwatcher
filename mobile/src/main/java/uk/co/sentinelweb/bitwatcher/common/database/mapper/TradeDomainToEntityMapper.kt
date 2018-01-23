package uk.co.sentinelweb.bitwatcher.common.database.mapper

import uk.co.sentinelweb.bitwatcher.common.database.entities.TradeEntity
import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.TransactionItemDomain
import javax.inject.Inject

class TradeDomainToEntityMapper @Inject constructor(){

    /**
     * Map trade entity - Account should exist first
     */
    fun map(input:TransactionItemDomain.TradeDomain, acct:AccountDomain):TradeEntity {
        return TradeEntity(
                null, // TODO add id to domain
                acct.id!!,
                input.date,
                input.transactionId,
                input.type,
                input.price,
                input.amount,
                input.currencyCodeFrom,
                input.currencyCodeTo,
                input.feesAmount,
                input.feesCurrencyCode,
                input.status
        )
    }
}