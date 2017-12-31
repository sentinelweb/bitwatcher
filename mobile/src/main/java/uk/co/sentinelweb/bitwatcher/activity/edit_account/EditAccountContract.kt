package uk.co.sentinelweb.bitwatcher.activity.edit_account

import android.os.Parcelable
import android.support.annotation.ColorInt
import android.support.annotation.IdRes
import uk.co.sentinelweb.bitwatcher.activity.edit_account.view.BalanceItemContract
import uk.co.sentinelweb.bitwatcher.common.validation.ValidationError
import uk.co.sentinelweb.domain.AccountDomain


interface EditAccountContract {

    interface View {

        fun createAndShowTypeDialog()
        fun updateState(state: EditAccountState)
        fun addBalanceView(): BalanceItemContract.Presenter
        fun removeBalanceView(index: Int)
        fun showError(@IdRes id: Int, error: ValidationError)
        fun showSnackBar(validation: ValidationError)
        fun showColorPicker(@ColorInt selectedColor:Int)
        fun close()
    }

    interface Presenter {
        fun onTypeChangeClick()
        fun onColorButtonClick()
        fun onColorSelected(@ColorInt color:Int)
        fun onTypeSelected(idx:Int)
        fun initialise(id: Long?)
        fun addBalance()
        fun saveAndFinish()
        fun validateName(value:String)
        fun getSaveState(): Parcelable
        fun restoreState(domain: AccountDomain)
    }
}