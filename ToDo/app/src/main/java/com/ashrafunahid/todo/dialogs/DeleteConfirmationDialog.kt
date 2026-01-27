package com.ashrafunahid.todo.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.ashrafunahid.todo.R

class DeleteConfirmationDialog(val callBack: () -> Unit): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("Delete")
        dialog.setIcon(R.drawable.baseline_delete_24)
        dialog.setMessage("Are you sure want to delete?")
        dialog.setPositiveButton("Yes", {dialog, which ->
            callBack()
        })
        dialog.setNegativeButton("No", null)
        return dialog.create()
    }
}