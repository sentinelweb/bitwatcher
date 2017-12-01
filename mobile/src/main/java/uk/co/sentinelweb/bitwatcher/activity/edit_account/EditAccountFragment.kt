package uk.co.sentinelweb.bitwatcher.activity.edit_account

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uk.co.sentinelweb.bitwatcher.R

import kotlinx.android.synthetic.main.fragment_edit_account.*
import uk.co.sentinelweb.bitwatcher.common.ui.picker.PickerItemModel
import uk.co.sentinelweb.bitwatcher.common.ui.picker.PickerPresenterFactory
import uk.co.sentinelweb.bitwatcher.common.ui.picker.PickerView
import uk.co.sentinelweb.bitwatcher.domain.AccountType
import javax.inject.Inject

class EditAccountFragment: Fragment(), EditAccountContract.View {

    @Inject lateinit var pickerPresenterFactory: PickerPresenterFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_account, container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type_edit.setOnClickListener({v -> showTypeDialog()})
    }

    private fun showTypeDialog() {
        val recyclerView = RecyclerView(context)
        val pickerPresenter = pickerPresenterFactory.createPresenter(PickerView<AccountType>(recyclerView))
        val models = mutableListOf<PickerItemModel<AccountType>>()
        for (accountType in AccountType.values()) {
            models.add(PickerItemModel(accountType.toString(), accountType))
        }
        pickerPresenter.bindData(models)
        AlertDialog.Builder(context)
                .setTitle("Select Type")
                .setView(recyclerView)
                .create()
                .show()
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}