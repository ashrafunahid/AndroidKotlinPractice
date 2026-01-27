package com.ashrafunahid.todo.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment

class DatePickerDialogFragment(val callback: (Long) -> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int  = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(
        p0: DatePicker?,
        p1: Int,
        p2: Int,
        p3: Int
    ) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(p1, p2, p3)
        val timeStamp: Long = calendar.timeInMillis
        callback(timeStamp)
    }
}