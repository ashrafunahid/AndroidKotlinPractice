package com.ashrafunahid.todo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashrafunahid.todo.entities.ToDoModel
import com.ashrafunahid.todo.repos.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModels @Inject constructor (val repository: ToDoRepository): ViewModel() {

    fun insertToDo(toDoModel: ToDoModel) {
        viewModelScope.launch {
            repository.insertToDo(toDoModel)
        }
    }

    fun fetchAllToDos(): LiveData<List<ToDoModel>> {
        return repository.getAllToDos()
    }

    fun editToDo(toDoModel: ToDoModel) {
        viewModelScope.launch {
            toDoModel.isComplete = !toDoModel.isComplete
            repository.editToDo(toDoModel)
        }
    }

    fun deleteToDo(toDoModel: ToDoModel) {
        viewModelScope.launch {
            repository.deleteToDo(toDoModel)
        }
    }
}