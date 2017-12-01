package uk.co.sentinelweb.bitwatcher.common.database.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import uk.co.sentinelweb.bitwatcher.common.database.converter.AccountTypeConverter
import uk.co.sentinelweb.bitwatcher.common.database.converter.BigDecimalConverter
import uk.co.sentinelweb.bitwatcher.common.database.converter.CurrencyCodeConverter
import uk.co.sentinelweb.bitwatcher.domain.AccountType

@Entity(tableName = "account")
@TypeConverters(AccountTypeConverter::class)
data class AccountEntity constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Long?,// TODO better way to autoincrement?  also  = UUID.randomUUID().toString()
        val name: String,
        val type: AccountType
)

//,
//        foreignKeys = arrayOf(
//        ForeignKey(entity = PositionItemEntity::class,
//                parentColumns = arrayOf("id"),
//                childColumns = arrayOf("account_id"),
//                onDelete = ForeignKey.CASCADE))