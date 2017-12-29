package uk.co.sentinelweb.bitwatcher.app

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.activity.edit_account.EditAccountComponent
import uk.co.sentinelweb.bitwatcher.activity.main.MainActivityComponent
import uk.co.sentinelweb.bitwatcher.receiver.AlarmReceiver
import javax.inject.Singleton


@Module(includes = arrayOf(BitwatcherAppModule.Bindings::class),
        subcomponents = arrayOf(MainActivityComponent::class, EditAccountComponent::class))
class BitwatcherAppModule {

    @Provides
    @Singleton
    fun provideAlarmReceiver() : AlarmReceiver{
        return AlarmReceiver()
    }

    @Provides
    @Singleton
    fun providePreferences(app:BitwatcherApplication) : SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(app)
    }

    @Provides
    @Singleton
    fun provideGson() : Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create()
    }

    @Module
    interface Bindings {
        @Binds
        @Singleton
        fun bindsContext(app:BitwatcherApplication):Context
    }
}