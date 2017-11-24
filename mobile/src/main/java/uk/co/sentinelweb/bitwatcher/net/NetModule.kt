package uk.co.sentinelweb.bitwatcher.net

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    fun provideTickerDataApiInteractor(): TickerDataApiInteractor = TickerDataApiInteractor()
}