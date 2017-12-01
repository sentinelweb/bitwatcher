package uk.co.sentinelweb.bitwatcher.common.ui.picker

import javax.inject.Inject

class PickerPresenterFactory @Inject constructor() {

    fun <T> createPresenter(pickerView: PickerContract.View<T>): PickerContract.Presenter<T> {
        val pickerPresenter = PickerPresenter(pickerView)
        pickerView.setPresenter(pickerPresenter)
        return pickerPresenter

    }
}
