package uk.co.sentinelweb.bitwatcher.activity.edit_account

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_edit_account.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.app.BitwatcherApplication
import uk.co.sentinelweb.bitwatcher.domain.AccountType
import javax.inject.Inject

class EditAccountFragment : Fragment(), EditAccountContract.View {


    @Inject lateinit var fragmentPresenter: EditAccountFragmentPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_account, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type_text.setOnClickListener({ _ -> fragmentPresenter.onTypeChangeClick() })
        (activity.applicationContext as BitwatcherApplication)
                .component
                .editAccountBuilder()
                .editAccountFragment(this)
                .build()
                .inject(this)
        fragmentPresenter.initialise(arguments.getLong(EditAccountActivity.EXTRA_ACCOUNT_ID))
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun createAndShowTypeDialog(){
        val items = arrayOfNulls<CharSequence>(AccountType.values().size)
        AccountType.values().forEachIndexed { index, type ->items[index]=type.toString() }
        AlertDialog.Builder(context)
                .setTitle(getString(R.string.title_select_type))
                .setItems(items, { d:DialogInterface, idx:Int -> fragmentPresenter.onTypeSelected(idx) })
                .create()
                .show()

    }

    override fun updateState(state: EditAccountState) {
        type_text.setText(state.type?.toString())
        name_edit.setText(state.name)

    }
}