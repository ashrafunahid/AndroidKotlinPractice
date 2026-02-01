package com.ashrafunahid.tourmate.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ashrafunahid.tourmate.models.ExpenseModel
import com.ashrafunahid.tourmate.models.MomentModel
import com.ashrafunahid.tourmate.models.TourModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class TourRepository {

    val db = Firebase.firestore
    fun addTour(tourModel: TourModel) {
        val docRef = db.collection(collection_tour).document()
        tourModel.id = docRef.id
        docRef.set(tourModel)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

    fun getToursByUser(userId: String): LiveData<List<TourModel>> {
        val tourListLiveData = MutableLiveData<List<TourModel>>()
        db.collection(collection_tour)
            .whereEqualTo("userId", userId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val tour = ArrayList<TourModel>()
                for (doc in value!!) {
                    tour.add(doc.toObject(TourModel::class.java))
                }
                tourListLiveData.postValue(tour)
            }
        return tourListLiveData
    }

    fun getTourById(tourId: String): LiveData<TourModel> {
        val tourLiveData = MutableLiveData<TourModel>()
        db.collection(collection_tour).document(tourId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                tourLiveData.postValue(value?.toObject(TourModel::class.java))
            }
        return tourLiveData
    }

    fun updateTourStatus(tourId: String, status: Boolean) {
        val docRef = db.collection(collection_tour).document(tourId)
        docRef.update(mapOf("completed" to status))
            .addOnSuccessListener {  }.addOnFailureListener {  }
    }

    fun addExpense(expenseModel: ExpenseModel, tourId: String) {
        val docRef = db.collection(collection_tour)
            .document(tourId)
            .collection(collection_expense)
            .document()
        expenseModel.expenseId = docRef.id
        docRef.set(expenseModel)
            .addOnSuccessListener { }
            .addOnFailureListener { }
    }

    fun getExpenses(tourId: String): LiveData<List<ExpenseModel>> {
        val expenseLiveData = MutableLiveData<List<ExpenseModel>>()
        db.collection(collection_tour).document(tourId).collection(collection_expense)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val expenseList = ArrayList<ExpenseModel>()
                for (expense in value!!) {
                    expenseList.add(expense.toObject(ExpenseModel::class.java))
                }
                expenseLiveData.postValue(expenseList)
            }

        return expenseLiveData
    }

    fun addMoment(momentModel: MomentModel, tourId: String) {
        val docRef = db.collection(collection_tour).document(tourId)
            .collection(collection_photos)
            .document()
        momentModel.momentId = docRef.id
        docRef.set(momentModel).addOnSuccessListener {  }.addOnFailureListener {  }
    }

    fun getAllMoments(tourId: String): LiveData<List<MomentModel>> {
        val momentsLiveData = MutableLiveData<List<MomentModel>> ()
        db.collection(collection_tour)
            .document(tourId)
            .collection(collection_photos)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val momentList = ArrayList<MomentModel>()
                for (moment in value!!) {
                    momentList.add(moment.toObject(MomentModel::class.java))
                }
                momentsLiveData.postValue(momentList)
            }
        return momentsLiveData
    }

    companion object {
        const val TAG = "db_error"
        const val collection_tour = "My Tours"
        const val collection_expense = "My Expenses"
        const val collection_photos = "My Photos"
    }
}