package uk.co.sentinelweb.bitwatcher.database

import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.app.BitwatcherApplication
import javax.inject.Singleton

@Module
class BitwatcherDbModule {

    @Provides
    @Singleton
    fun provideInMemoryDb(app:BitwatcherApplication):BitwatcherDatabase {
        return Room.inMemoryDatabaseBuilder<BitwatcherDatabase>(app, BitwatcherDatabase::class.java)
                .build();
    }

    @Provides
    @Singleton
    fun provideDbInitialiser(db:BitwatcherDatabase):DbInitialiser {
        return DbInitialiser(db);
    }
}