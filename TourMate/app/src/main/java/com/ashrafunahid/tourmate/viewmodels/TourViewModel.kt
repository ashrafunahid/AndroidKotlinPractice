package com.ashrafunahid.tourmate.viewmodels

import androidx.lifecycle.ViewModel
import com.ashrafunahid.tourmate.models.ExpenseModel
import com.ashrafunahid.tourmate.models.TourModel
import com.ashrafunahid.tourmate.repositories.TourRepository

class TourViewModel : ViewModel() {
    private val repository = TourRepository()
    fun addTour(tourModel: TourModel) {
        repository.addTour(tourModel)
    }

    fun getToursByUser(userId: String) = repository.getToursByUser(userId)

    fun getTourById(tourId: String) = repository.getTourById(tourId)

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

}