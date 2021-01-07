package com.opa.android.growwassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.opa.android.growwassignment.R
import com.opa.android.growwassignment.databinding.RowDayBinding
import com.opa.android.growwassignment.model.Day

class DayAdapter : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    val dayList = ArrayList<Day>()

    fun setDayList(dayList: List<Day>) {
        this.dayList.clear()
        this.dayList.addAll(dayList)
        notifyDataSetChanged()
    }

    inner class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RowDayBinding.bind(itemView)

        fun bind(day: Day) {
            with(binding) {
                tvDay.text = day.date.toString()

                if (day.isSelected) {
                    tvDay.setBackgroundColor(
                        getColorRes(
                            itemView.context,
                            R.color.purple_700
                        )
                    )

                    tvDay.setTextColor(
                        getColorRes(
                            itemView.context,
                            R.color.white
                        )
                    )
                } else {
                    tvDay.setBackgroundColor(
                        getColorRes(
                            itemView.context,
                            R.color.white
                        )
                    )

                    tvDay.setTextColor(
                        getColorRes(
                            itemView.context,
                            R.color.black
                        )
                    )
                }
            }
        }
    }

    private fun getColorRes(context: Context, color: Int): Int {
        return ContextCompat.getColor(context, color)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        return DayViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.row_day,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(dayList[position])
    }

}