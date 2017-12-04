package uk.co.sentinelweb.bitwatcher.activity.edit_account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
        if (savedInstanceState == null) {
            EditAccountFragment().addWithExtras(this, R.id.activity_root)
        }
    }
}