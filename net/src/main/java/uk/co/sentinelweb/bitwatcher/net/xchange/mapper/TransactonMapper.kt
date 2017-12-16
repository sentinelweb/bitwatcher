package uk.co.sentinelweb.bitwatcher.net.xchange.mapper

import org.knowm.xchange.dto.account.FundingRecord
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TransactionDomain
import uk.co.sentinelweb.domain.TransactionDomain.TransactionStatus.*
import uk.co.sentinelweb.domain.TransactionDomain.TransactionType.WITHDRAWL
import java.math.BigDecimal

class TransactonMapper() {
        fun map(transactions: List<FundingRecord>): List<TransactionDomain> {
            val result = mutableListOf<TransactionDomain>()
            transactions.forEach {
                val type = mapOrderType(it.type)
                result.add(TransactionDomain(
                        type,
                        it.date,
                        it.amount,
                        CurrencyCode.lookup(it.currency.currencyCode),
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
                FundingRecord.Type.DEPOSIT -> return TransactionDomain.TransactionType.DEPOSIT
                FundingRecord.Type.WITHDRAWAL -> return WITHDRAWL
                else -> return TransactionDomain.TransactionType.UNKNOWN
            }
        }
    }