package uk.co.sentinelweb.bitwatcher.activity.edit_account


interface EditAccountContract {

    interface View {

        fun createAndShowTypeDialog()
        fun updateState(state: EditAccountState)
    }

    interface Presenter {

        fun onTypeChangeClick()
        fun onTypeSelected(idx:Int)
        fun initialise(id: Long?)
    }
}