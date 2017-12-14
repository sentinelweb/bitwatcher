package uk.co.sentinelweb.bitwatcher.activity.edit_account

import android.support.annotation.IdRes
import uk.co.sentinelweb.bitwatcher.activity.edit_account.view.BalanceItemContract
import uk.co.sentinelweb.bitwatcher.common.validation.ValidationError
import uk.co.sentinelweb.domain.AccountDomain
import java.io.Serializable


interface EditAccountContract {

    interface View {

        fun createAndShowTypeDialog()
        fun updateState(state: EditAccountState)
        fun addBalanceView(): BalanceItemContract.Presenter
        fun removeBalanceView(index: Int)
        fun showError(@IdRes id: Int, error: ValidationError)
        fun showSnackBar(validation: ValidationError)
        fun close()
    }

    interface Presenter {
        fun onTypeChangeClick()
        fun onTypeSelected(idx:Int)
        fun initialise(id: Long?)
        fun addBalance()
        fun saveAndFinish()
        fun validateName(value:String)
        fun getSaveState(): Serializable
        fun restoreState(domain: AccountDomain)
    }
}