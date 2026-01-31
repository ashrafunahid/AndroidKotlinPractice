package com.ashrafunahid.tourmate.models

import com.google.firebase.Timestamp

data class TourModel(
    var id: String? = null,
    var userId: String? = null,
    var title: String? = null,
    var destination: String? = null,
    var budget: Int? = null,
    var isCompleted: Boolean = false,
    var createdOn: Timestamp = Timestamp.now()
)