package com.ashrafunahid.todo.di

import android.content.Context
import com.ashrafunahid.todo.daos.ToDoDao
import com.ashrafunahid.todo.db.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun provideToDoDao(@ApplicationContext context: Context) : ToDoDao {
        return ToDoDatabase.getDb(context).getTodoDao()
    }
}