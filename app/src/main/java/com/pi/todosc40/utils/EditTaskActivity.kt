package com.pi.todosc40.utils

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pi.todosc40.database.MyDataBase
import com.pi.todosc40.database.entity.Todo
import com.pi.todosc40.databinding.ActivityEditTaskBinding
import java.util.Calendar

class EditTaskActivity : AppCompatActivity() {

    lateinit var binding:ActivityEditTaskBinding
    lateinit var todo:Todo
    var selectedDay=Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        todo=getTodoObject()
        databinding(todo)
        initDatePickerDialog()
        initListeners()
    }

    private fun databinding(todo: Todo) {

        binding.tflTitle.editText?.setText(todo.title)
        binding.tflTaskdetails.editText?.setText(todo.description)

        val date=Calendar.getInstance()
        date.timeInMillis=todo.date
        val year=date.get(Calendar.YEAR)
        val month=date.get(Calendar.MONTH)
        val day=date.get(Calendar.DAY_OF_MONTH)
        binding.selectDate.text="$day / ${month + 1} / $year"
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener {
            if (!isValidInputs()) return@setOnClickListener
            selectedDay.clearTime()
            val title = binding.tflTitle.editText!!.text.toString()
            val description = binding.tflTaskdetails.editText!!.text.toString()
            todo.title=title
            todo.description=description
            todo.date=selectedDay.timeInMillis
            MyDataBase.getInstance(this).getTodosDao().updateTodo(todo)
            onsavebuttonclick?.refresh()
            finish()
        }

    }

    private fun isValidInputs(): Boolean {
        val title = binding.tflTitle.editText!!.text
        val description = binding.tflTaskdetails.editText!!.text
        var isValid = true
        if (title.isNullOrEmpty()) {
            binding.tflTitle.error = "Please enter valid title"
            isValid = false
        } else {
            binding.tflTitle.error = null
        }
        if (description.isNullOrEmpty()) {
            binding.tflTaskdetails.error = "Please enter valid description"
            isValid = false
        } else {
            binding.tflTitle.error = null
        }
        return isValid
    }

    private fun initDatePickerDialog() {
        binding.selectDate.setOnClickListener {
            val dialog = DatePickerDialog(
                this,
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
        binding.selectDate.text = "$day / ${month + 1} / $year"
    }


    @SuppressLint("NewApi")
    private fun getTodoObject(): Todo {
        return intent.getParcelableExtra(ObjectTodo,Todo::class.java)!!
    }

    companion object{
        val ObjectTodo="todo object"
        var onsavebuttonclick:EditTaskActivity.onSaveButtonClick?=null
    }


    fun interface onSaveButtonClick{
        fun refresh()
    }
}