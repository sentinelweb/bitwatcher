package uk.co.sentinelweb.bitwatcher.activity.edit_account

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import uk.co.sentinelweb.domain.AccountDomain

@SuppressLint("ParcelCreator")
@Parcelize
data class EditAccountStateParcel constructor(
        var account: AccountDomain? = null
) : Parcelable
