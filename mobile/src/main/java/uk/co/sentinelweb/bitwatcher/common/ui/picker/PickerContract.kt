package uk.co.sentinelweb.bitwatcher.common.ui.picker

interface PickerContract {

    interface Presenter<T> {

        val selected: PickerItemModel<T>

        fun bindData(items: List<PickerItemModel<T>>)

        fun setInitialPosition(position: Int)

        fun updatePositionFromScroll(position: Int)
    }

    interface View<T> {

        fun setPresenter(presenter: Presenter<T>)

        fun setData(items: List<PickerItemModel<T>>)

        fun setInitialPosition(position: Int)
    }
}
