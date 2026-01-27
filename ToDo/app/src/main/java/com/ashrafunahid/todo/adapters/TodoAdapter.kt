package com.ashrafunahid.todo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashrafunahid.todo.R
import com.ashrafunahid.todo.databinding.TodoItemBinding
import com.ashrafunahid.todo.entities.ToDoModel
import com.ashrafunahid.todo.utils.delete_todo
import com.ashrafunahid.todo.utils.edit_todo
import com.ashrafunahid.todo.utils.getFormattedDateTime
import com.ashrafunahid.todo.utils.priority_low
import com.ashrafunahid.todo.utils.priority_normal

class TodoAdapter(val actionCallBack: (ToDoModel, String) -> Unit) : ListAdapter<ToDoModel, TodoAdapter.ToDoViewHolder>(ToDoDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ToDoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding, actionCallBack)
    }

    override fun onBindViewHolder(
        holder: ToDoViewHolder,
        position: Int
    ) {
        val toDoModel = getItem(position)
        holder.bind(toDoModel)
    }

    class ToDoViewHolder(private val binding: TodoItemBinding, val actionCallBack: (ToDoModel, String) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(toDoModel: ToDoModel) {
            binding.todo = toDoModel
//            binding.todoNameRow.text = toDoModel.name
            binding.todoDateRow.text = "${getFormattedDateTime(toDoModel.date, "dd/M/YYYY")}"
            binding.todoTimeRow.text = "${getFormattedDateTime(toDoModel.time, "hh:mm a")}"
            binding.completeCheckBox.isChecked = toDoModel.isComplete
            val iconID = when (toDoModel.priority) {
                priority_low -> R.drawable.baseline_greenstar_24
                priority_normal -> R.drawable.baseline_bluestar_24
                else -> R.drawable.baseline_redstar_24
            }
            binding.priorityImage.setImageResource(iconID)
            binding.completeCheckBox.setOnClickListener {
                actionCallBack(toDoModel, edit_todo)
            }
            binding.dotMenuBtn.setOnClickListener {
                val popUpMenu = PopupMenu(it.context, it)
                popUpMenu.menuInflater.inflate(R.menu.todo_row_menu, popUpMenu.menu)
                popUpMenu.show()
                popUpMenu.setOnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.item_delete -> {
                            actionCallBack(toDoModel, delete_todo)
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }
}

class ToDoDiffCallback : DiffUtil.ItemCallback<ToDoModel>() {
    override fun areItemsTheSame(
        oldItem: ToDoModel,
        newItem: ToDoModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ToDoModel,
        newItem: ToDoModel
    ): Boolean {
        return oldItem == newItem
    }

}