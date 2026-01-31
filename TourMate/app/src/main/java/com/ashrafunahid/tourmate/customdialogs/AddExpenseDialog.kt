package com.ashrafunahid.tourmate.customdialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.ashrafunahid.tourmate.R
import com.ashrafunahid.tourmate.models.ExpenseModel

class AddExpenseDialog(val callback: (ExpenseModel) -> Unit): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val layout = requireActivity().layoutInflater.inflate(R.layout.add_expense_layout, null)
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle("Add Expense")
            .setView(layout)
            .setPositiveButton("Add") {dialog, which ->
                val titleEt: EditText = layout.findViewById(R.id.addExpenseTitleET)
                val amountEt: EditText = layout.findViewById(R.id.addExpenseAmountET)

                if (titleEt.text.isNullOrEmpty() || amountEt.text.isNullOrEmpty()) {
                    titleEt.error = "Title is required"
                    amountEt.error = "Amount is required"
                } else {
                    val title = titleEt.text.toString()
                    val amount = amountEt.text.toString().toInt()
                    val expenseModel = ExpenseModel(title = title, amount = amount)
                    callback(expenseModel)
                }
            }
            .setNegativeButton("cancel", null)

        return builder.create()
    }
}