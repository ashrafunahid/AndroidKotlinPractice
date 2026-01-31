package com.ashrafunahid.tourmate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ashrafunahid.tourmate.R
import com.ashrafunahid.tourmate.customdialogs.AddExpenseDialog
import com.ashrafunahid.tourmate.customdialogs.ShowExpenseListDialog
import com.ashrafunahid.tourmate.databinding.FragmentTourDetailsBinding
import com.ashrafunahid.tourmate.models.ExpenseModel
import com.ashrafunahid.tourmate.viewmodels.TourViewModel

class TourDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTourDetailsBinding
    private var tourId: String? = null
    private val tourViewModel: TourViewModel by viewModels()
    private var expenseList = listOf<ExpenseModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTourDetailsBinding.inflate(inflater, container, false)

        val args: TourDetailsFragmentArgs by navArgs()
        tourId = args.tourId

        tourId?.let { id ->
            tourViewModel.getTourById(id).observe(viewLifecycleOwner, {tour ->
                binding.tourModel = tour
            })
        }

        tourId?.let { tourId ->
            tourViewModel.getExpenses(tourId).observe(viewLifecycleOwner, { expenses->
                expenseList = expenses
                val totalExpense = tourViewModel.getTotalExpenseAmount(expenseList)
                binding.totalExpense = totalExpense
            })
        }

        binding.detailsAddExpenseBtn.setOnClickListener {
            tourId?.let { id ->
                AddExpenseDialog{ model ->
                    tourViewModel.addExpense(expenseModel = model, tourId = id)
                }.show(childFragmentManager, "add_expense_dialog")
            }
        }

        binding.detailsViewExpenseBtn.setOnClickListener {
            ShowExpenseListDialog(expenseList).show(childFragmentManager, "show_expense_list_dialog")
        }

        return binding.root
    }

}