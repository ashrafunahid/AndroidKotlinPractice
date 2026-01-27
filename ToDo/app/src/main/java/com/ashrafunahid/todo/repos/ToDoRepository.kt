package com.ashrafunahid.todo.repos

import android.content.Context
import androidx.lifecycle.LiveData
import com.ashrafunahid.todo.daos.ToDoDao
import com.ashrafunahid.todo.db.ToDoDatabase
import com.ashrafunahid.todo.entities.ToDoModel
import javax.inject.Inject

class ToDoRepository @Inject constructor(val toDoDao: ToDoDao) {

    suspend fun insertToDo(toDoModel: ToDoModel) {
        toDoDao.saveTodo(toDoModel)
    }

    fun getAllToDos(): LiveData<List<ToDoModel>> {
        return toDoDao.getAllTodos()
    }

    suspend fun editToDo(toDoModel: ToDoModel) {
        toDoDao.updateTodo(toDoModel)
    }

    suspend fun deleteToDo(toDoModel: ToDoModel) {
        toDoDao.deleteTodo(toDoModel)
    }
}