package com.ashrafunahid.todo.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ashrafunahid.todo.R
import com.ashrafunahid.todo.utils.getFormattedDateTime
import com.ashrafunahid.todo.utils.priority_low
import com.ashrafunahid.todo.utils.priority_normal

@BindingAdapter("android:setPriorityIcon")
fun setPriorityIcon(imageView: ImageView, priority: String?) {
    val iconResource = when (priority) {
        priority_low -> R.drawable.baseline_greenstar_24
        priority_normal -> R.drawable.baseline_bluestar_24
        else -> R.drawable.baseline_redstar_24
    }
    imageView.setImageResource(iconResource)
}

@BindingAdapter("android:setFormattedDate")
fun setFormattedDate(textView: TextView, date: Long) {
    textView.text = getFormattedDateTime(date, "dd/MM/yyyy")
}

@BindingAdapter("android:setFormattedTime")
fun setFormattedTime(textView: TextView, time: Long) {
    textView.text = getFormattedDateTime(time, "hh:mm a")
}
