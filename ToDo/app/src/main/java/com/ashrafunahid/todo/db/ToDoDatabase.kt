package com.ashrafunahid.todo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ashrafunahid.todo.daos.ToDoDao
import com.ashrafunahid.todo.entities.ToDoModel

@Database(entities = [ToDoModel::class], version = 1)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun getTodoDao(): ToDoDao

    companion object {
        private var toDoDatabase: ToDoDatabase? = null
        fun getDb(context: Context): ToDoDatabase {
            return toDoDatabase ?: synchronized(this) {
                val db = Room.databaseBuilder(context, ToDoDatabase::class.java, "todo_db")
                    .build()
                toDoDatabase = db
                db
            }
        }
    }
}