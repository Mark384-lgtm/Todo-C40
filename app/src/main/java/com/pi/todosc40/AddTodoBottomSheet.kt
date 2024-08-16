package com.pi.todosc40

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.core.widget.addTextChangedListener

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pi.todosc40.databinding.BottomSheetAddTodoBinding
import java.time.Year
import java.util.Calendar

class AddTodoBottomSheet : BottomSheetDialogFragment() {
    lateinit var binding: BottomSheetAddTodoBinding
    var selectedDay = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDatePickerDialog()
        binding.titleTextInputLayout.editText?.addTextChangedListener {
            isValidInputs()
        }
    }

    fun isValidInputs(): Boolean{
        val title = binding.titleTextInputLayout.editText!!.text
        val description = binding.descriptionTextInputLayout.editText!!.text
        var isValid = true
        if(title.isNullOrEmpty()){
            binding.titleTextInputLayout.error = "Please enter valid title"
            isValid = false
        }else {
            binding.titleTextInputLayout.error = null
        }
        if(description.isNullOrEmpty()){
            binding.descriptionTextInputLayout.error = "Please enter valid description"
            isValid = false;
        }else {
            binding.descriptionTextInputLayout.error = null
        }
        return isValid;
    }

    private fun initDatePickerDialog() {
        binding.dateTv.setOnClickListener {
            val dialog = DatePickerDialog(
                requireContext(),
                { p0, year, month, day ->
                    selectedDay.set(Calendar.YEAR, year)
                    selectedDay.set(Calendar.MONTH, month)
                    selectedDay.set(Calendar.DAY_OF_MONTH, day)
                    updateDateTextView()
                }, selectedDay.get(Calendar.YEAR),
                selectedDay.get(Calendar.MONTH),
                selectedDay.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }
    }

    private fun updateDateTextView() {
        val year = selectedDay.get(Calendar.YEAR)
        val month = selectedDay.get(Calendar.MONTH)
        val day = selectedDay.get(Calendar.DAY_OF_MONTH)
        binding.dateTv.text = "$day / ${month + 1} / $year"
    }

}