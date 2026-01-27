package com.example.bmicalculator.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BmiViewModels: ViewModel() {
    var bmi: MutableLiveData<Double> = MutableLiveData()
    var category: MutableLiveData<String> = MutableLiveData()
    fun CalculateBMI(weight: Double, height: Double){
        bmi.value = weight / (height * height)
        category.value = when(String.format("%.2f", bmi.value).toDouble()) {
            in 0.0 .. 16.6 -> severeThinnes
            in 16.0 .. 16.9 -> moderateThinness
            in 17.0 .. 18.4 -> mildThinness
            in 18.5 .. 24.9 -> normal
            in 25.0 .. 29.9 -> preObese
            in 30.0 .. 34.9 -> class1
            in 35.0 .. 39.9 -> class2
            else -> class3
        }
    }
    companion object {
        val severeThinnes = "Severe Thinness"
        val moderateThinness = "Moderate Thinness"
        val mildThinness = "Mild Thinness"
        val normal = "Normal"
        val preObese = "Pre-Obese"
        val class1 = "Obese Class I"
        val class2 = "Obese Class II"
        val class3 = "Obese Class III"
    }
}