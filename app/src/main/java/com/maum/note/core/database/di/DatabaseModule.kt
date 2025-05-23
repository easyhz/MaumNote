package com.maum.note.core.database.di

import android.content.Context
import androidx.room.Room
import com.maum.note.core.database.AppDatabase
import com.maum.note.core.database.note.dao.NoteDao
import com.maum.note.core.database.tone.dao.ToneDao
import com.maum.note.core.database.util.migration.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database.db"
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(appDatabase: AppDatabase): NoteDao {
        return appDatabase.noteDao
    }

    @Provides
    @Singleton
    fun provideToneDao(appDatabase: AppDatabase): ToneDao {
        return appDatabase.toneDao
    }
}