package uk.co.sentinelweb.bitwatcher.activity.edit_account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.common.extensions.addWithExtras

class EditAccountActivity : AppCompatActivity() {

    companion object {
        //val FRAGMENT_TAG = "edit_account_fragment"
        val EXTRA_ACCOUNT_ID = "account_id"

        fun launch(c: Context, accountId: Long): Intent {
            val intent = Intent(c, EditAccountActivity::class.java)
            intent.putExtra(EXTRA_ACCOUNT_ID, accountId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)
        EditAccountFragment().addWithExtras(this, R.id.activity_root)
    }
}