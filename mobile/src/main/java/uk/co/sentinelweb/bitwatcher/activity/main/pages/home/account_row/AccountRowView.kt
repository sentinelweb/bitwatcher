package uk.co.sentinelweb.bitwatcher.activity.main.pages.home.account_row

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.PopupMenu
import kotlinx.android.synthetic.main.main_home_account_row.view.*
import uk.co.sentinelweb.bitwatcher.R

class AccountRowView(context: Context?) : FrameLayout(context), AccountRowContract.View {

    lateinit var presenter: AccountRowContract.Presenter

    init {
        LayoutInflater.from(context).inflate(R.layout.main_home_account_row, this, true)
        setOnClickListener({ _ -> presenter.onClick() })
        account_row_checkbox.setOnCheckedChangeListener({ _, checked -> presenter.onCheckClick(checked) })
        account_row_overflow_button.setOnClickListener({ _ -> presenter.onOverflowClick() })
    }

    override fun setPresenter(accountRowPresenter: AccountRowPresenter) {
        this.presenter = accountRowPresenter
    }

    override fun updateView(data: AccountRowState.DisplayData) {
        account_row_checkbox.text = data.nameText
        account_row_checkbox.setTextColor(ContextCompat.getColor(context, data.nameTextColor))
        account_row_balances_text.text = data.balancesText
        account_row_total.text = data.totalText
    }

    override fun setVisible(visible: Boolean) {
        this.visibility = if (visible) View.VISIBLE else View.GONE
    }


    override fun showOverflow() {
        val popupMenu = PopupMenu(context, account_row_overflow_button)
        popupMenu.inflate(R.menu.account_row_popup)
        popupMenu.setOnMenuItemClickListener({ item ->
            when (item.itemId) {
                R.id.account_row_delete -> {
                    presenter.onDeleteClick(); true
                }
                R.id.account_row_edit -> {
                    presenter.onEditClick(); true
                }
                else -> false
            }
        })
        popupMenu.show()
    }
}