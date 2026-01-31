package com.ashrafunahid.tourmate.customdialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ashrafunahid.tourmate.R
import com.ashrafunahid.tourmate.adapters.ExpenseAdapter
import com.ashrafunahid.tourmate.models.ExpenseModel

class ShowExpenseListDialog(val list: List<ExpenseModel>): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layout = requireActivity().layoutInflater.inflate(R.layout.view_expense_layout, null)

        val recyclerView = layout.findViewById<RecyclerView>(R.id.expenseRV)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        val adapter = ExpenseAdapter().apply {
            submitList(list)
        }
        recyclerView.adapter = adapter

        val builder = AlertDialog.Builder(requireActivity())
            .setTitle("Expense List")
            .setView(layout)
            .setNegativeButton("Close", null)
        return builder.create()
    }

}