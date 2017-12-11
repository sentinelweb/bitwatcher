package uk.co.sentinelweb.bitwatcher.activity.edit_account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_with_fragment.*
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.common.extensions.addWithExtras

/**
 * This activity uses a more traditional android architecture with a simple container activity and a fragment
 */
class EditAccountActivity : AppCompatActivity() {

    companion object {
        val EXTRA_ACCOUNT_ID = "account_id"

        fun getLaunchIntent(c: Context, accountId: Long?): Intent {
            val intent = Intent(c, EditAccountActivity::class.java)
            if (accountId != null) {
                intent.putExtra(EXTRA_ACCOUNT_ID, accountId)
            }
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragment)
        setSupportActionBar(toolbar)
        supportActionBar?.subtitle = getString(R.string.title_edit_account)
        supportActionBar?.setIcon(R.drawable.ll_action_bar_icon)

        if (savedInstanceState == null) {
            EditAccountFragment().addWithExtras(this, R.id.fragment_root)
        }
    }
}