package com.ashrafunahid.tourmate.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.ashrafunahid.tourmate.models.ExpenseModel
import com.ashrafunahid.tourmate.models.MomentModel
import com.ashrafunahid.tourmate.models.TourModel
import com.ashrafunahid.tourmate.repositories.TourRepository
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

class TourViewModel : ViewModel() {
    private val repository = TourRepository()
    fun addTour(tourModel: TourModel) {
        repository.addTour(tourModel)
    }

    fun getToursByUser(userId: String) = repository.getToursByUser(userId)

    fun getTourById(tourId: String) = repository.getTourById(tourId)

    fun updateTourStatus(tourId: String, status: Boolean) = repository.updateTourStatus(tourId, status)

    fun addExpense(expenseModel: ExpenseModel, tourId: String) =
        repository.addExpense(expenseModel, tourId)

    fun getExpenses(tourId: String) = repository.getExpenses(tourId)

    fun getTotalExpenseAmount(expenseList: List<ExpenseModel>): Int {
        var totalExpense = 0
        expenseList.forEach { expense ->
            totalExpense += expense.amount!!
        }
        return totalExpense
    }

    fun uploadImageToStorage(bitmap: Bitmap, tourId: String, tourName: String) {
        val imageName = "${tourId}_${System.currentTimeMillis()}.jpg"
        val imageRef = Firebase.storage.reference.child("$tourName/$imageName")
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream)
        val imageData = byteArrayOutputStream.toByteArray()
        val uploadTask = imageRef.putBytes(imageData)
        uploadTask.addOnSuccessListener {  }.addOnFailureListener {  }
        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { exception ->
                    throw exception
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                val momentModel = MomentModel(imageName = imageName, imageUrl = downloadUri.toString())
                repository.addMoment(momentModel, tourId)
            }
        }
    }

    fun getAllMoments(tourId: String) = repository.getAllMoments(tourId)

}