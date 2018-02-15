package uk.co.sentinelweb.bitwatcher.common.database

import android.arch.persistence.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import uk.co.sentinelweb.bitwatcher.app.BitwatcherApplication
import uk.co.sentinelweb.bitwatcher.common.database.migration.Migration1To2
import uk.co.sentinelweb.bitwatcher.common.database.migration.Migration2to3
import uk.co.sentinelweb.bitwatcher.common.database.migration.Migration3to4
import uk.co.sentinelweb.bitwatcher.common.database.repository.PositionItemRoomRepository
import uk.co.sentinelweb.bitwatcher.common.database.test.DbInitialiser
import uk.co.sentinelweb.bitwatcher.common.repository.BalanceRepository
import javax.inject.Singleton

@Module(includes = arrayOf(BitwatcherDbModule.Bindings::class))
class BitwatcherDbModule {

//    @Provides
//    @Singleton
//    fun provideInMemoryDb(app:BitwatcherApplication): BitwatcherMemoryDatabase {
//        return Room.inMemoryDatabaseBuilder<BitwatcherMemoryDatabase>(app, BitwatcherMemoryDatabase::class.java)
//                .build()
//    }

    @Provides
    @Singleton
    fun provideDb(app:BitwatcherApplication): BitwatcherDatabase {
        return Room.databaseBuilder(app, BitwatcherDatabase::class.java, "BitwatcherDatabase.db")
                .addMigrations(Migration1To2(), Migration2to3(), Migration3to4())
                .build()
    }

//    @Provides
//    @Singleton
//    fun provideDbMemInitialiser(db: BitwatcherMemoryDatabase): DbMemoryInitialiser {
//        return DbMemoryInitialiser(db)
//    }
    @Provides
    @Singleton
    fun provideDbInitialiser(db: BitwatcherDatabase): DbInitialiser {
        return DbInitialiser(db)
    }

    @Module
    interface Bindings {

        @Binds
        @Singleton
        fun bindsBalanceRepo(impl:PositionItemRoomRepository):BalanceRepository
    }
}