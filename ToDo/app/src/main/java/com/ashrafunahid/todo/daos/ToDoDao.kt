package com.ashrafunahid.todo.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ashrafunahid.todo.entities.ToDoModel

@Dao
interface ToDoDao {
    @Insert
    suspend fun saveTodo(toDoModel: ToDoModel)

    @Update
    suspend fun updateTodo(toDoModel: ToDoModel)

    @Delete
    suspend fun deleteTodo(toDoModel: ToDoModel)

    @Query("select * from tbl_todo order by id desc")
    fun getAllTodos(): LiveData<List<ToDoModel>>
}