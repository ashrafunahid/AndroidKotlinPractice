package com.ashrafunahid.todo.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment

class TimePickerDialogFragment(val callback: (Long) -> Unit): DialogFragment(), TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar: Calendar = Calendar.getInstance()
        val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)
        val minute: Int = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(requireActivity(), this, hour, minute, false)
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(0, 0, 0, p1, p2)
        val timeStamp: Long = calendar.timeInMillis
        callback(timeStamp)
    }
}