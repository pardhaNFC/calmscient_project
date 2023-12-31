/*
 *
 *      Copyright (c) 2023- NFC Solutions, - All Rights Reserved
 *      All source code contained herein remains the property of NFC Solutions Incorporated
 *      and protected by trade secret or copyright law of USA.
 *      Dissemination, De-compilation, Modification and Distribution are strictly prohibited unless
 *      there is a prior written permission or license agreement from NFC Solutions.
 *
 *      Author : @Pardha Saradhi
 */

package com.calmscient.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.calmscient.R
import com.calmscient.adapters.SummaryofMoodFragmentAdapter
import com.calmscient.data.remote.WeeklySummaryMoodTask
import com.calmscient.databinding.CalendarDayLayoutBinding
import com.calmscient.databinding.SummaryofauditFragmentBinding
import com.calmscient.utils.getColorCompat
import com.kizitonwose.calendar.core.Week
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.yearMonth
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SummaryOfAUDITFragment: Fragment() {
    private lateinit var binding: SummaryofauditFragmentBinding
    private lateinit var dateView: TextView
    private lateinit var calenderDateView: TextView
    private var selectedDate = LocalDate.now()
    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    private val summaryCardViewItems = mutableListOf<WeeklySummaryMoodTask>()
    private lateinit var summaryOfMoodAdapter: SummaryofMoodFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            loadFragment(WeeklySummaryFragment())
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SummaryofauditFragmentBinding.inflate(inflater, container, false)
        binding.backIcon.setOnClickListener {
            loadFragment(WeeklySummaryFragment())
        }
        binding.calenderview.setOnClickListener {
            binding.newbackIcon.visibility = View.VISIBLE
            binding.graphScreen.visibility = View.GONE
            binding.datesScreen.visibility = View.VISIBLE
            binding.backIcon.visibility = View.GONE
            binding.scrollViewScreen.visibility = View.GONE
        }
        binding.newbackIcon.setOnClickListener {
            binding.backIcon.visibility = View.VISIBLE
            binding.graphScreen.visibility = View.VISIBLE
            binding.datesScreen.visibility = View.GONE
            binding.newbackIcon.visibility = View.GONE
            binding.scrollViewScreen.visibility = View.VISIBLE

        }
        binding.needToTalkWithSomeOne.setOnClickListener {
            loadFragment(EmergencyResourceFragment())
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Ensure that dateView is part of the FragmentWeeklysummarymoodBinding layout
        val dateView: TextView = binding.dateView
        val calenderDateView: TextView = binding.calenderDateView
        // Get the current date from the calendar
        val calendar = Calendar.getInstance()
        val currentDate: Date = calendar.time

        // Format the current date and calculate the date for next month
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val currentDateString: String = dateFormat.format(currentDate)

        // Calculate the date for next month
        calendar.add(Calendar.MONTH, 1)
        val nextMonthDate: Date = calendar.time
        val nextMonthDateString: String = dateFormat.format(nextMonthDate)

        // Create the final date string
        val finalDateString = "$currentDateString - $nextMonthDateString"

        // Set the date in the TextView
        dateView.text = finalDateString
        calenderDateView.text = finalDateString
        class DayViewContainer(view: View) : ViewContainer(view) {
            val bind = CalendarDayLayoutBinding.bind(view)
            lateinit var day: WeekDay

            init {
                view.setOnClickListener {
                    if (selectedDate != day.date) {
                        val oldDate = selectedDate
                        selectedDate = day.date
                        binding.exSevenCalendar.notifyDateChanged(day.date)
                        oldDate?.let { binding.exSevenCalendar.notifyDateChanged(it) }
                    }
                }
            }
            fun bind(day: WeekDay) {
                this.day = day
                bind.exSevenDateText.text = dateFormatter.format(day.date)
                bind.exSevenDayText.text = day.date.dayOfWeek.displayText()
                val colorRes = if (day.date == selectedDate) {
                    R.color.white
                } else {
                    R.color.black
                }
                bind.exSevenDateText.setTextColor(view.context.getColorCompat(colorRes))
                bind.exSevenDayText.setTextColor(view.context.getColorCompat(colorRes))
                val colorResLayout = if (day.date == selectedDate) {
                    R.drawable.calendar_custom_border
                } else {
                    R.color.example_7_calendar
                }
                bind.layoutDate.setBackgroundResource(colorResLayout)
                //bind.exSevenSelectedView.isVisible = day.date == selectedDate
            }
        }
        binding.exSevenCalendar.dayBinder = object : WeekDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: WeekDay) = container.bind(data)
        }
        binding.exSevenCalendar.weekScrollListener = { weekDays ->
            val text = binding.exSevenToolbar.title
            binding.exSevenToolbar.setTitleTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
        }
        val currentMonth = YearMonth.now()
        binding.exSevenCalendar.setup(
            currentMonth.minusMonths(24).atStartOfMonth(),
            currentMonth.plusMonths(24).atEndOfMonth(),
            firstDayOfWeekFromLocale(),
        )
        binding.exSevenCalendar.scrollToDate(LocalDate.now())
        binding.recyclerViewSummaryMood.layoutManager = LinearLayoutManager(requireContext())
        summaryOfMoodAdapter = SummaryofMoodFragmentAdapter(summaryCardViewItems )
        binding.recyclerViewSummaryMood.adapter = summaryOfMoodAdapter
        summaryOfMoodAdapter.updateTasks(summaryCardViewItems)
        displayCardViewsForSelectedDate()
    }

    private fun displayCardViewsForSelectedDate() {
        // Format the selected date as "MM/dd/yyyy" and set it as the first dateview1
        val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        summaryCardViewItems.clear()
        // Get the selected date
        val selectedDate = selectedDate
        for (i in 0 until 7) {
            val description = getCardDescription(i)
            val nextDate = selectedDate.plusDays(i.toLong())
            val nextDateStr = nextDate.format(dateFormatter)
            summaryCardViewItems.add(
                WeeklySummaryMoodTask(
                    nextDateStr,
                    description,
                    null
                )
            )
        }
        summaryOfMoodAdapter.notifyDataSetChanged()
    }

    private fun getCardDescription(cardPosition: Int): String {
        return when (cardPosition) {
            0 -> getString(R.string.audit_text1_weeklysummary)
            1 -> getString(R.string.audit_text2_weeklysummary)
            2 -> getString(R.string.audit_text2_weeklysummary)
            3 -> getString(R.string.audit_text2_weeklysummary)
            4 -> getString(R.string.audit_text2_weeklysummary)
            5 -> getString(R.string.audit_text2_weeklysummary)
            else -> getString(R.string.audit_text1_weeklysummary)
        }
    }
    fun getWeekPageTitle(week: Week): String {
        val firstDate = week.days.first().date
        val lastDate = week.days.last().date
        return when {
            firstDate.yearMonth == lastDate.yearMonth -> {
                firstDate.yearMonth.displayText()
            }
            firstDate.year == lastDate.year -> {
                "${firstDate.month.displayText(short = false)} - ${lastDate.yearMonth.displayText()}"
            }
            else -> {
                "${firstDate.yearMonth.displayText()} - ${lastDate.yearMonth.displayText()}"
            }
        }
    }
    fun YearMonth.displayText(short: Boolean = false): String {
        return "${this.month.displayText(short = short)} ${this.year}"
    }
    fun Month.displayText(short: Boolean = true): String {
        val style = if (short) TextStyle.SHORT else TextStyle.FULL
        return getDisplayName(style, Locale.ENGLISH)
    }
    fun DayOfWeek.displayText(uppercase: Boolean = false): String {
        return getDisplayName(TextStyle.SHORT, Locale.ENGLISH).let { value ->
            if (uppercase) value.uppercase(Locale.ENGLISH) else value
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.flFragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}