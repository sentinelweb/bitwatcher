package uk.co.sentinelweb.bitwatcher.net.xchange.mapper

import org.knowm.xchange.dto.account.FundingRecord
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TransactionItemDomain.TransactionDomain
import uk.co.sentinelweb.domain.TransactionItemDomain.TransactionDomain.TransactionStatus.*
import uk.co.sentinelweb.domain.TransactionItemDomain.TransactionDomain.TransactionType.*
import java.math.BigDecimal

class TransactonMapper() {
        fun map(transactions: List<FundingRecord>): List<TransactionDomain> {
            val result = mutableListOf<TransactionDomain>()
            transactions.forEach {
                val type = mapOrderType(it.type)
                result.add(TransactionDomain(
                        it.internalId ?: "noId",
                        it.date,
                        it.amount,
                        CurrencyCode.lookup(it.currency.currencyCode),
                        type,
                        it.balance ?: BigDecimal.ZERO,
                        it.description ?: "",
                        mapStatus(it.status),
                        it.fee
                ))
            }
            return result.toList()
        }

        private fun mapStatus(status: FundingRecord.Status?): TransactionDomain.TransactionStatus {
            when (status) {
                FundingRecord.Status.PROCESSING -> return PROCESSING
                FundingRecord.Status.CANCELLED -> return CANCELLED
                FundingRecord.Status.COMPLETE -> return COMPLETE
                FundingRecord.Status.FAILED -> return FAILED
                else -> return TransactionDomain.TransactionStatus.UNKNOWN
            }
        }
        
        fun mapOrderType(t: FundingRecord.Type): TransactionDomain.TransactionType {
            when (t) {
                FundingRecord.Type.DEPOSIT -> return DEPOSIT
                FundingRecord.Type.WITHDRAWAL -> return WITHDRAWL
                else -> return TransactionDomain.TransactionType.UNKNOWN
            }
        }
    }