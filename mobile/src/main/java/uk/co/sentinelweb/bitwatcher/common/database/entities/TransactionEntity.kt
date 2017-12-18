package uk.co.sentinelweb.bitwatcher.common.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import uk.co.sentinelweb.bitwatcher.common.database.converter.*
import uk.co.sentinelweb.domain.CurrencyCode
import uk.co.sentinelweb.domain.TransactionItemDomain
import java.math.BigDecimal
import java.util.*

@Entity(tableName = "transaction")
@TypeConverters(AccountTypeConverter::class, DateConverter::class, CurrencyCodeConverter::class, BigDecimalConverter::class, TransactionTypeConverter::class, TransactionStatusConverter::class)
class TransactionEntity constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Long?,// TODO better way to autoincrement?  also  = UUID.randomUUID().toString()
        @ColumnInfo(name = "account_id")
        val accountId: Long,
        val type: TransactionItemDomain.TransactionDomain.TransactionType,
        val date: Date,
        val amount: BigDecimal,
        val currencyCode: CurrencyCode,
        val balance: BigDecimal,
        val description: String,
        val status: TransactionItemDomain.TransactionDomain.TransactionStatus,
        val fee: BigDecimal,
        val feeCurrency: CurrencyCode
) {
}