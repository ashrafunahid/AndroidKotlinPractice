package com.ashrafunahid.tourmate.models

import com.google.firebase.Timestamp

data class MomentModel(
    var momentId: String? = null,
    var imageName: String? = null,
    var imageUrl: String? = null,
    var timestamp: Timestamp = Timestamp.now()
)