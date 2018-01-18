package uk.co.sentinelweb.bitwatcher.activity.main.pages.trade

import uk.co.sentinelweb.bitwatcher.R
import uk.co.sentinelweb.bitwatcher.common.extensions.dp
import uk.co.sentinelweb.bitwatcher.common.mapper.AccountBalancesMapper
import uk.co.sentinelweb.bitwatcher.common.mapper.CurrencyPairMapper
import uk.co.sentinelweb.bitwatcher.common.mapper.StringMapper
import uk.co.sentinelweb.domain.CurrencyPair
import javax.inject.Inject


class TradeDisplayMapper @Inject constructor(
        private val balancesMapper: AccountBalancesMapper,
        private val stringMapper: StringMapper,
        private val marketMapper: CurrencyPairMapper
        ) {

    fun mapDisplay(state: TradeState): TradeState.TradeDisplayModel {
        return TradeState.TradeDisplayModel(
                balancesMapper.mapBalances(state.account),
                state.account?.name ?: stringMapper.getString(R.string.trade_select_account),
                state.currentPrice.dp(5),
                if (state.market != CurrencyPair.NONE) marketMapper.mapName(state.market) else stringMapper.getString(R.string.trade_select_market)
        )
    }

}
