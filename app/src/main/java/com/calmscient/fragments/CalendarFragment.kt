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

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.calmscient.Interface.CellClickListener
import com.calmscient.R
import com.calmscient.adapters.MedicationsCardAdapter
import com.calmscient.databinding.CalendarDayLayoutBinding
import com.calmscient.databinding.CalendarFragmentLayoutBinding
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
import java.util.GregorianCalendar
import java.util.Locale


// Define the CardViewItem data class
data class CardViewItem(
    val title: String,
    val dosage: String,
    val timeMorning: String?,
    val timeEvening: String?,
    val sunImageResource: Int?,
    val moonImageResource: Int?
)

class CalendarFragment : Fragment(), CellClickListener {
    private lateinit var binding: CalendarFragmentLayoutBinding
    private var selectedDate = LocalDate.now()
    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    private lateinit var cardViewAdapter: MedicationsCardAdapter
    private val cardViewItems = mutableListOf<CardViewItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this){
            loadFragment(MedicalRecordsFragment())
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CalendarFragmentLayoutBinding.inflate(inflater, container, false)
        binding.backIcon.setOnClickListener {
            loadFragment(MedicalRecordsFragment())
        }

        binding.saveButton.setOnClickListener {
            loadFragment(MedicalRecordsFragment())
        }
        binding.plusIcon.setOnClickListener {
            loadFragment(AddMedicationsFragment())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        class DayViewContainer(view: View) : ViewContainer(view) {
            val bind = CalendarDayLayoutBinding.bind(view)
            lateinit var day: WeekDay

            init {
                view.setOnClickListener {
                    if (selectedDate != day.date) {
                        val oldDate = selectedDate
                        selectedDate = day.date
                        /*Toast.makeText(requireActivity(), "date:" + selectedDate, Toast.LENGTH_LONG)
                            .show()*/
                        incrementDateByOne()
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
            binding.exSevenToolbar.title = getWeekPageTitle(weekDays)
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
        binding.recyclerViewMedications.layoutManager = LinearLayoutManager(requireContext())
        cardViewAdapter = MedicationsCardAdapter(cardViewItems)
        binding.recyclerViewMedications.adapter = cardViewAdapter

        // Call this function when the date is selected
        displayCardViewsForSelectedDate()
    }

    private fun displayCardViewsForSelectedDate() {
        cardViewItems.clear()
        cardViewItems.addAll(
            listOf(
                CardViewItem(
                    getString(R.string.paracetamol),
                    getString(R.string.two_tablets),
                    "8:00 AM",
                    "7:00 PM",
                    R.drawable.sunset,
                    R.drawable.moon
                ),
                CardViewItem(getString(R.string.anti_biotic),  getString(R.string.one_tablet), "9:30 AM", null, R.drawable.sunset, null),
                CardViewItem(
                    getString(R.string.vitamin),
                    getString(R.string.two_tablets),
                    "9:00 AM",
                    "7:30 PM",
                    R.drawable.sunset,
                    R.drawable.moon
                )
            )
        )
        cardViewAdapter.notifyDataSetChanged()
    }

    fun incrementDateByOne() {
       /* val c = Calendar.getInstance()
        c.time = date
        c.add(Calendar.DATE, 1)
        return c.time*/

        val sdf = SimpleDateFormat("MM/dd/yyyy")
        for (i in 0..6) {
            val calendar: Calendar = GregorianCalendar()
            calendar.add(Calendar.DATE, i)
            val day: String = sdf.format(calendar.time)
            Log.i(TAG, day)
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

    override fun onCellClickListener(position: Int) {

    }
}