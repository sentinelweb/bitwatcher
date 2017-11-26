package uk.co.sentinelweb.bitwatcher.app

import dagger.Module
import uk.co.sentinelweb.bitwatcher.activity.MainActivityComponent

@Module(subcomponents = arrayOf(MainActivityComponent::class))
class BitwatcherAppModule {

}