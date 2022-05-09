package com.example.emotioncalendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.emotioncalendar.databinding.FragmentCalendarBinding
import com.example.emotioncalendar.viewmodel.CalendarViewModel
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthScrollListener
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

@AndroidEntryPoint
class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var binder: DayDataBinder

    private val viewModel: CalendarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        initCalendar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelInit()
    }

    private fun viewModelInit() {
        viewModel.currentYearMonth.observe(viewLifecycleOwner) { yearMonth ->
            binding.yearView.text = yearMonth.year.toString()
            binding.monthView.text = yearMonth.month.toString()
        }

        viewModel.calendar.observe(viewLifecycleOwner) { calendar ->
            binder.updateCalendar(calendar)
        }

        viewModel.getCalendar()
    }

    private fun initCalendar() {
        binder = DayDataBinder(binding.calendarView).apply {
            onDayClick = ::onClickDate
        }
        binding.calendarView.dayBinder = binder

        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(120)
        val lastMonth = currentMonth.plusMonths(12)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek

        binding.calendarView.setup(firstMonth, lastMonth, firstDayOfWeek)
        binding.calendarView.scrollToMonth(currentMonth)

        binding.calendarView.monthScrollListener = object : MonthScrollListener {
            override fun invoke(date: CalendarMonth) {
                viewModel.onScrolled(date.yearMonth)
            }
        }

        binding.diaryButtonView.setOnClickListener {
            navigateToMemoList()
        }
    }

    private fun onClickDate(date: LocalDate) {
        val dateId = date.format(
            DateTimeFormatter.ofPattern("YYYY년 MM월 dd일").withLocale(Locale.forLanguageTag("ko"))
        )
        Log.e("Jungbong", dateId)

        viewModel.calendar.value?.get(dateId)?.let {
            Log.e("Jungbong", it.toString())
            navigateToDiaryDetail(dateId)
        } ?: run {
            navigateToWrite(date)
        }
    }

    private fun navigateToMemoList() {
        val direction =
            CalendarFragmentDirections.actionCalendarFragmentToMemoListFragment()
        findNavController().navigate(direction)
    }

    private fun navigateToWrite(date: LocalDate) {
        val direction =
            CalendarFragmentDirections.actionCalendarFragmentToWriteFragment(date)
        findNavController().navigate(direction)
    }

    private fun navigateToDiaryDetail(date: String) {
        val direction =
            CalendarFragmentDirections.actionCalendarFragmentToMemoDetailFragment(date)
        findNavController().navigate(direction)
    }

}