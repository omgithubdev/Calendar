package com.opa.android.growwassignment.utils

import com.opa.android.growwassignment.model.Day
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object CustomCalendar {
    val month31Days = listOf(1, 3, 5, 7, 8, 10, 12)
    val month30Days = listOf(4, 6, 9, 11)

    val minYear = 1800
    val maxYear = 2100

    val calendar: Calendar = Calendar.getInstance()

    val FORMAT_DDMMYYYY = "dd/MM/yyyy"

    fun getCurrentDay(): Int {
        return calendar.get(Calendar.DATE)
    }

    fun getCurrentMonth(): Int {
        return calendar.get(Calendar.MONTH)
    }

    fun getCurrentYear(): Int {
        return calendar.get(Calendar.YEAR)
    }

    fun getDayList(month: Int, year: Int): List<Day> {
        val list: ArrayList<Day> = ArrayList()
        if (month31Days.contains(month)) {
            for (i in 1..31)
                list.add(Day(i))
        } else if (month30Days.contains(month)) {
            for (i in 1..30)
                list.add(Day(i))
        } else {
            if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
                for (i in 1..29)
                    list.add(Day(i))
            } else {
                for (i in 1..28)
                    list.add(Day(i))
            }
        }

        return list
    }

    fun isValidDate(date: Int, month: Int, year: Int): Boolean {
        if (year > maxYear || year < minYear)
            return false

        if (month < 1 || month > 12)
            return false

        val list = getDayList(month, year)

        if (list.contains(Day(date))) {
            return true
        } else {
            return false
        }
    }

    fun getWeek(date: String): String {
        val inputDate: Date = SimpleDateFormat(FORMAT_DDMMYYYY)
            .parse(date)
        val cal = Calendar.getInstance()
        cal.time = inputDate

        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val firstWkDay: String = java.lang.String.valueOf(cal.getTime())

        cal.add(Calendar.DAY_OF_WEEK, 6)
        val lastWkDay: String = java.lang.String.valueOf(cal.getTime())

        return "Week: $firstWkDay \n to $lastWkDay"
    }

    fun getYearList(): ArrayList<Int> {
        val yearList: ArrayList<Int> = ArrayList()
        for (i in 1800..2100) {
            yearList.add(i)
        }

        return yearList
    }
}