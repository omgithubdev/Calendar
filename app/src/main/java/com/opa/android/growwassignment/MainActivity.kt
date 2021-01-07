package com.opa.android.growwassignment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.opa.android.growwassignment.adapter.DayAdapter
import com.opa.android.growwassignment.databinding.ActivityMainBinding
import com.opa.android.growwassignment.utils.CustomCalendar
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter = DayAdapter()

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
    }

    fun initialize() {
        initViews()
        setListener()
    }

    fun setListener() {
        binding.spMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateDays()
            }
        }

        binding.spYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                updateDays()
            }
        }

        binding.etDate.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length == 2 || s.toString().length == 5) {
                    val sb = StringBuffer(s)
                    sb.append("/")
                    binding.etDate.setText(sb)
                    Selection.setSelection(binding.etDate.text, binding.etDate.text.length)
                }
            }
        })

        binding.btnSearch.setOnClickListener {
            val strDate = binding.etDate.text.toString()
            if (strDate.length < 10) {
                Toast.makeText(
                    this,
                    getString(R.string.error_valid_date),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val date = strDate.split("/")

                val day = date[0].toInt()
                val month = date[1].toInt()
                val year = date[2].toInt()

                if (CustomCalendar.isValidDate(day, month, year)) {
                    binding.spMonth.setSelection(month - 1)
                    binding.spYear.setSelection(year - 1800)


                    val list = CustomCalendar.getDayList(month, year)
                    list.forEach {
                        if (it.date == day) {
                            it.isSelected = true
                        } else {
                            it.isSelected = false
                        }
                    }

                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            adapter.setDayList(list)
                        }
                    }, 500)
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.error_valid_date),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        binding.btnWeek.setOnClickListener {
            val strDate = binding.etDate.text.toString()
            if (strDate.length < 10) {
                Toast.makeText(
                    this,
                    getString(R.string.error_valid_date),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val date = strDate.split("/")

                val day = date[0].toInt()
                val month = date[1].toInt()
                val year = date[2].toInt()

                if (CustomCalendar.isValidDate(day, month, year)) {
                    binding.tvWeek.setText(CustomCalendar.getWeek(strDate))
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.error_valid_date),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        binding.btnClear.setOnClickListener {
            reset()
        }
    }

    private fun reset(){
        binding.etDate.setText("")
        binding.tvWeek.setText(getString(R.string.label_week))
    }

    private fun updateDays() {
        adapter.setDayList(
            CustomCalendar.getDayList(
                binding.spMonth.selectedItemPosition + 1,
                binding.spYear.selectedItem as Int
            )
        )
    }

    private fun initViews() {
        binding.rvDays.adapter = adapter

        ArrayAdapter.createFromResource(
            this,
            R.array.months_name,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spMonth.adapter = adapter
        }

        val yearList = CustomCalendar.getYearList()

        ArrayAdapter<Int>(
            this,
            android.R.layout.simple_spinner_item,
            yearList
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spYear.adapter = adapter
        }

        binding.spMonth.setSelection(CustomCalendar.getCurrentMonth())
        binding.spYear.setSelection(yearList.indexOf(CustomCalendar.getCurrentYear()))
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}