package com.ashrafunahid.todo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ashrafunahid.todo.R
import com.ashrafunahid.todo.utils.priority_normal
import com.ashrafunahid.todo.databinding.FragmentNewToDoBinding
import com.ashrafunahid.todo.db.ToDoDatabase
import com.ashrafunahid.todo.dialogs.DatePickerDialogFragment
import com.ashrafunahid.todo.dialogs.TimePickerDialogFragment
import com.ashrafunahid.todo.entities.ToDoModel
import com.ashrafunahid.todo.utils.getFormattedDateTime
import com.ashrafunahid.todo.viewmodels.ToDoViewModels
import com.ashrafunahid.todo.workmanagerutils.WorkManagerService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewToDoFragment : Fragment() {

    private lateinit var binding: FragmentNewToDoBinding
    private var todoName: String = ""
    private var priority: String = priority_normal
    private var dateInMillis: Long = System.currentTimeMillis()
    private var timeInMillis: Long = System.currentTimeMillis()
    private val toDoViewModel: ToDoViewModels by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewToDoBinding.inflate(inflater, container, false)

        binding.priorityGroup.setOnCheckedChangeListener { radioGroup, i ->
            val rb = radioGroup.findViewById<RadioButton>(i)
            priority = rb.text.toString()
        }

        binding.datePickerBtn.setOnClickListener {
            DatePickerDialogFragment { timeStamp ->
                dateInMillis = timeStamp
                binding.datePickerBtn.text = getFormattedDateTime(dateInMillis, "d/M/yyyy")
            }.show(childFragmentManager, "date_picker")
        }

        binding.timePickerBtn.setOnClickListener {
            TimePickerDialogFragment({ timeStamp ->
                timeInMillis = timeStamp
                binding.timePickerBtn.text = getFormattedDateTime(timeInMillis,"hh:mm a")
            }).show(childFragmentManager, "time_picker")
        }

        binding.saveBtn.setOnClickListener {
            todoName = binding.todoName.text.toString()
            if (todoName.isEmpty()) {
                binding.todoName.error = "Please provide a valid todo name"
                return@setOnClickListener
            }
            val todo: ToDoModel = ToDoModel(name = todoName, priority = priority, date = dateInMillis, time = timeInMillis)
            toDoViewModel.insertToDo(todo)
            WorkManagerService(requireContext()).schedule(todoName, 5000)
            findNavController().navigate(R.id.todo_list_fragment)
        }

        return binding.root
    }

}