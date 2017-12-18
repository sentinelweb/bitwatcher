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

@Entity(tableName = "trade")
@TypeConverters(AccountTypeConverter::class, DateConverter::class, CurrencyCodeConverter::class, BigDecimalConverter::class, TradeTypeConverter::class)
data class TradeEntity constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Long?,// TODO better way to autoincrement?  also  = UUID.randomUUID().toString()
        @ColumnInfo(name = "account_id")
        val accountId: Long,
        val date: Date,
        val tid: String,
        val type: TransactionItemDomain.TradeDomain.TradeType,
        val price: BigDecimal,
        val amount: BigDecimal,
        val currencyCodeFrom: CurrencyCode,
        val currencyCodeTo: CurrencyCode,
        val feesAmount: BigDecimal,
        val feesCurrency: CurrencyCode
) {
}