package uk.co.sentinelweb.bitwatcher.activity.edit_account

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.IdRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import com.android.colorpicker.ColorPickerPalette
import kotlinx.android.synthetic.main.edit_account_fragment.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.activity.edit_account.EditAccountActivity.Companion.EXTRA_ACCOUNT_ID
import uk.co.sentinelweb.bitwatcher.activity.edit_account.view.BalanceItemContract
import uk.co.sentinelweb.bitwatcher.activity.edit_account.view.BalanceItemPresenter
import uk.co.sentinelweb.bitwatcher.activity.edit_account.view.BalanceItemView
import uk.co.sentinelweb.bitwatcher.app.BitwatcherApplication
import uk.co.sentinelweb.bitwatcher.common.ui.AndroidUtils
import uk.co.sentinelweb.bitwatcher.common.validation.ValidationError
import uk.co.sentinelweb.domain.AccountType
import uk.co.sentinelweb.domain.ColourDomain
import javax.inject.Inject


class EditAccountFragment : Fragment(), EditAccountContract.View {
    companion object {
        val TAG = EditAccountFragment::class.java.simpleName
        val STATE_KEY = "ACCOUNT_DOMAIN"
    }

    @Inject lateinit var fragmentPresenter: EditAccountFragmentPresenter
    var snackBar: Snackbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edit_account_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type_text.setOnClickListener({ _ -> fragmentPresenter.onTypeChangeClick() })
        (activity?.applicationContext as BitwatcherApplication)
                .component
                .editAccountBuilder()
                .editAccountFragment(this)
                .build()
                .inject(this)

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_KEY)) {
            val restoreState = savedInstanceState.getParcelable<EditAccountStateParcel>(STATE_KEY)
            Log.d(TAG, "restoring account : ${restoreState}")
            restoreState.account?.let { fragmentPresenter.restoreState(it) }
        } else {
            val id = if (arguments?.containsKey(EXTRA_ACCOUNT_ID)?: false) arguments?.getLong(EXTRA_ACCOUNT_ID) else null
            fragmentPresenter.initialise(id)
        }
        balances_add_button.setOnClickListener({ fragmentPresenter.addBalance() })
        name_edit.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                fragmentPresenter.validateName(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
        color_picker.setOnClickListener({ fragmentPresenter.onColorButtonClick() })
        this.lifecycle.addObserver(fragmentPresenter)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit_activity, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_account_ok -> {
                AndroidUtils.hideSoftKeyboard(name_edit)
                fragmentPresenter.saveAndFinish(); return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun createAndShowTypeDialog() {
        val items = arrayOfNulls<CharSequence>(AccountType.values().size - 1)
        AccountType.values().forEachIndexed { index, type -> if (index > 0) items[index - 1] = type.toString() }
        AlertDialog.Builder(context!!)
                .setTitle(getString(R.string.title_select_type))
                .setItems(items, { _: DialogInterface, idx: Int -> fragmentPresenter.onTypeSelected(idx + 1) })
                .create()
                .show()

    }

    override fun updateState(state: EditAccountState) {
        type_text.setText(state.type?.toString())
        name_edit.setText(state.name)
        val colorDrawable = ColorDrawable()
        colorDrawable.color = ColourDomain.toInteger(state.colour)
        color_picker.setImageDrawable(colorDrawable)
    }

    override fun addBalanceView(): BalanceItemContract.Presenter {
        val view = BalanceItemView(context)
        balances_edit_container.addView(view)
        val presenter = BalanceItemPresenter(view)
        return presenter
    }

    override fun removeBalanceView(index: Int) {
        balances_edit_container.removeViewAt(index)
    }

    override fun showError(@IdRes id: Int, error: ValidationError) {
        when (id) {
            R.id.name_edit -> name_error_text.text = error.message
        }
    }

    override fun showSnackBar(validation: ValidationError) {
        snackBar?.dismiss()
        snackBar = Snackbar.make(view!!, validation.message, Snackbar.LENGTH_SHORT)
        snackBar?.show()
    }

    override fun close() {
        activity?.finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val saveState = fragmentPresenter.getSaveState()
        Log.d(TAG, "saving account : ${saveState}")
        outState.putParcelable(STATE_KEY, saveState)
    }

    @SuppressLint("InflateParams")
    override fun showColorPicker(@ColorInt selectedColor: Int) {
        val colours = (context?.getResources()?.getIntArray(R.array.color_selection) ?: intArrayOf())
//                .map{ContextCompat.getColor(context!!, it)}
//                .toIntArray()
        var dialog:AlertDialog? = null
        val layoutInflater = LayoutInflater.from(context)
        //noinspection inflateparams
        val colorPickerPalette = layoutInflater.inflate(R.layout.view_colour_picker, null) as ColorPickerPalette
        colorPickerPalette.init(colours.size, 5, { c: Int ->
            fragmentPresenter.onColorSelected(c)
            dialog?.dismiss()
        })
        colorPickerPalette.drawPalette(colours, selectedColor)

        dialog = AlertDialog.Builder(context!!, R.style.ColourDialogTheme)
                .setTitle(R.string.title_select_color)
                .setView(colorPickerPalette)
                .create()
        dialog.show()
    }


}
