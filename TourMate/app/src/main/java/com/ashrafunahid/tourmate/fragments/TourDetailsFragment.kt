package com.ashrafunahid.tourmate.fragments

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ashrafunahid.tourmate.R
import com.ashrafunahid.tourmate.customdialogs.AddExpenseDialog
import com.ashrafunahid.tourmate.customdialogs.ShowExpenseListDialog
import com.ashrafunahid.tourmate.customdialogs.ShowMomentListDialog
import com.ashrafunahid.tourmate.databinding.FragmentTourDetailsBinding
import com.ashrafunahid.tourmate.models.ExpenseModel
import com.ashrafunahid.tourmate.models.MomentModel
import com.ashrafunahid.tourmate.viewmodels.TourViewModel

class TourDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTourDetailsBinding
    private var tourId: String? = null
    private var tourName: String? = null
    private val tourViewModel: TourViewModel by viewModels()
    private var expenseList = listOf<ExpenseModel>()
    private var momentsList = listOf<MomentModel>()

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bitmap = result.data?.extras?.get("data") as Bitmap
            tourViewModel.uploadImageToStorage(bitmap = bitmap, tourId = tourId!!, tourName = tourName!!)
        }
    }

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
                tourName = tour.title
            })
        }

        tourId?.let { tourId ->
            tourViewModel.getExpenses(tourId).observe(viewLifecycleOwner, { expenses->
                expenseList = expenses
                val totalExpense = tourViewModel.getTotalExpenseAmount(expenseList)
                binding.totalExpense = totalExpense
            })
        }

        tourId?.let { tourId ->
            tourViewModel.getAllMoments(tourId).observe(viewLifecycleOwner, {moments ->
                momentsList = moments
                binding.totalMoments = momentsList.size
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

        binding.captureImageBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                cameraLauncher.launch(intent)
            } catch (e: ActivityNotFoundException) {}
        }

        binding.galleryBtn.setOnClickListener {
            ShowMomentListDialog(momentsList).show(childFragmentManager, "show_moment_list_dialog")
        }

        return binding.root
    }

}