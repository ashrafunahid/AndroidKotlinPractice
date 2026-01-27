package com.ashrafunahid.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ashrafunahid.todo.R
import com.ashrafunahid.todo.adapters.TodoAdapter
import com.ashrafunahid.todo.databinding.FragmentToDoListBinding
import com.ashrafunahid.todo.db.ToDoDatabase
import com.ashrafunahid.todo.dialogs.DeleteConfirmationDialog
import com.ashrafunahid.todo.entities.ToDoModel
import com.ashrafunahid.todo.utils.delete_todo
import com.ashrafunahid.todo.utils.edit_todo
import com.ashrafunahid.todo.viewmodels.ToDoViewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToDoListFragment : Fragment() {

    private lateinit var binding: FragmentToDoListBinding
    private val toDoViewModel: ToDoViewModels by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoListBinding.inflate(inflater, container, false)

        val adapter = TodoAdapter(::toDoAction)
        binding.todoListRecycler.layoutManager = LinearLayoutManager(activity)
        binding.todoListRecycler.adapter = adapter

        toDoViewModel.fetchAllToDos()
            .observe(viewLifecycleOwner, { toDoList ->
                adapter.submitList(toDoList)
            })

        binding.newTodoBtn.setOnClickListener {
            findNavController().navigate(R.id.goto_new_todo_fragment)
        }

        return binding.root
    }
    private fun toDoAction(toDoModel: ToDoModel, tag: String) {
        when (tag) {
            edit_todo -> toDoViewModel.editToDo(toDoModel)
            delete_todo -> {
                DeleteConfirmationDialog({
                    toDoViewModel.deleteToDo(toDoModel)
                }).show(childFragmentManager, "delete")
            }
        }
    }
}