package uk.co.sentinelweb.bitwatcher.app

import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.activity.main.MainActivityComponent
import uk.co.sentinelweb.bitwatcher.receiver.AlarmReceiver
import javax.inject.Singleton

@Module(subcomponents = arrayOf(MainActivityComponent::class))
class BitwatcherAppModule {

    @Provides
    @Singleton
    fun provideAlarmReceiver() : AlarmReceiver{
        return AlarmReceiver()
    }
}