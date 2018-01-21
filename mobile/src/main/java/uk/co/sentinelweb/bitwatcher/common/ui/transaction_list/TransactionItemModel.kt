package uk.co.sentinelweb.bitwatcher.common.ui.transaction_list

import uk.co.sentinelweb.domain.AccountDomain
import uk.co.sentinelweb.domain.TransactionItemDomain

class TransactionItemModel constructor(
        val domain: TransactionItemDomain,
        val account: AccountDomain
)