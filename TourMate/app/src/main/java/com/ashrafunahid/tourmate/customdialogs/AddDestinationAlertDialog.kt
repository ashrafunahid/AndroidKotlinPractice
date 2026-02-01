package com.ashrafunahid.tourmate.customdialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.ashrafunahid.tourmate.R

class AddDestinationAlertDialog(val callback: (String) -> Unit): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val layout = requireActivity().layoutInflater.inflate(R.layout.add_destination_alert_layout, null)

        val builder = AlertDialog.Builder(requireActivity())
            .setTitle("Add Destination")
            .setView(layout)
            .setPositiveButton("Add", {dialog, which ->
                val titleEt: EditText = layout.findViewById(R.id.addDestinationAlertET)
                callback(titleEt.text.toString())
            })
            .setNegativeButton("Cancel", null)
        return builder.create()
    }
}