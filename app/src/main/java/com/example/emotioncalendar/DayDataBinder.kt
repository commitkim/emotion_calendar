package com.example.emotioncalendar

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.emotioncalendar.databinding.ItemCalendarDayBinding
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

class DayDataBinder(
    private val calendarView: CalendarView,
    private val context: Context = calendarView.context
): DayBinder<DayViewContainer> {
    private var calendar:  Map<String,Int> = HashMap<String,Int>()
    var onDayClick: ((LocalDate) -> Unit)? = null

    fun updateCalendar(
        calendar: Map<String,Int>
    ) {
        this.calendar = calendar
        this.calendarView.notifyCalendarChanged()
    }

    override fun create(view: View): DayViewContainer {
        val binding = ItemCalendarDayBinding.bind(view)
        return DayViewContainer(binding)
    }

    override fun bind(container: DayViewContainer, day: CalendarDay) {
        calendar[day.date.format(DateTimeFormatter.ofPattern("YYYY년 MM월 dd일").withLocale(Locale.forLanguageTag("ko")))]?.let {
            container.binding.imageView.background = ContextCompat.getDrawable(context,it)
            container.binding.imageView.visibility = View.VISIBLE
        }?: run{
            container.binding.imageView.visibility = View.GONE
        }

        container.binding.calendarDayText.text = day.date.dayOfMonth.toString()

        if(day.owner != DayOwner.THIS_MONTH) {
            container.binding.calendarDayText.visibility = View.GONE
            container.binding.root.setOnClickListener(null)
            if(day.date.isAfter(LocalDate.now())){
                container.binding.root.setOnClickListener(null)
                container.binding.calendarDayText.setTextColor(ContextCompat.getColor(context,R.color.gray))
            }
        } else {
            container.binding.root.setOnClickListener {
                onDayClick?.invoke(day.date)
            }
            container.binding.calendarDayText.visibility = View.VISIBLE
            when(day.date.dayOfWeek){
                DayOfWeek.SUNDAY -> {
                    container.binding.calendarDayText.setTextColor(ContextCompat.getColor(context,R.color.red))
                }
                DayOfWeek.SATURDAY -> {
                    container.binding.calendarDayText.setTextColor(ContextCompat.getColor(context,R.color.blue))
                }
                else -> {
                    container.binding.calendarDayText.setTextColor(ContextCompat.getColor(context,R.color.black))
                }
            }
        }
    }

}

class DayViewContainer(
    val binding: ItemCalendarDayBinding
    ) : ViewContainer(binding.root) {
}