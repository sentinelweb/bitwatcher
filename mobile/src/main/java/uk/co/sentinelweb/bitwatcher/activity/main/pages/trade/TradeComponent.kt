package uk.co.sentinelweb.bitwatcher.activity.pages.trade

import dagger.Subcomponent
import uk.co.sentinelweb.bitwatcher.common.scope.PageScope

@Subcomponent(modules = arrayOf(TradeModule::class))
@PageScope
interface TradeComponent {

    fun provideTradePresenter(): TradeContract.Presenter

    @Subcomponent.Builder
    interface Builder {

        fun build(): TradeComponent
    }
}