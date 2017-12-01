package uk.co.sentinelweb.bitwatcher.common.ui.picker

class PickerPresenter<T>(private val view: PickerContract.View<T>) : PickerContract.Presenter<T> {

    private var selectedPosition: Int = 0
    private var items: List<PickerItemModel<T>>? = null

    override val selected: PickerItemModel<T>
        get() = items!![selectedPosition]

    override fun bindData(items: List<PickerItemModel<T>>) {
        this.items = items
        view.setData(items)
    }

    override fun setInitialPosition(position: Int) {
        selectedPosition = position
        view.setInitialPosition(selectedPosition)
    }

    override fun updatePositionFromScroll(position: Int) {
        selectedPosition = position
    }
}
