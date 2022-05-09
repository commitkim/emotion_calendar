package com.example.emotioncalendar

import android.icu.util.LocaleData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.emotioncalendar.adapter.EmotionListAdapter
import com.example.emotioncalendar.data.Emotion
import com.example.emotioncalendar.data.Memo
import com.example.emotioncalendar.databinding.FragmentWriteBinding
import com.example.emotioncalendar.viewmodel.WriteViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class WriteFragment : Fragment(), EmotionListAdapter.OnClickEmotionListener {

    private val args: WriteFragmentArgs by navArgs()
    private lateinit var binding : FragmentWriteBinding
    private val viewModel: WriteViewModel by viewModels()

    private lateinit var emotion: Emotion
    private lateinit var day: LocalDate

    @Inject
    lateinit var emotionListAdapter: EmotionListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWriteBinding.inflate(inflater, container, false)
        this.day = args.day
        init()

        return binding.root
    }

    private fun init() {
        with(binding.recyclerView) {
            adapter = emotionListAdapter.apply {
                onClickEmotionListener = this@WriteFragment
            }
            emotionListAdapter.submitList(
                listOf(
                    Emotion(R.drawable.smile),
                    Emotion(R.drawable.not_bad),
                    Emotion( R.drawable.soso),
                    Emotion( R.drawable.sad),
                    Emotion( R.drawable.mola),
                )
            )
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    override fun invoke(emotion: Emotion) {
        this.emotion = emotion

        binding.choiceButton.setOnClickListener {
            binding.emotionChoiceView.visibility = View.GONE
            binding.writeView.visibility = View.VISIBLE

            binding.imageView.background = ContextCompat.getDrawable(binding.imageView.context, emotion.drawable)
            binding.dateView.text = day.format(DateTimeFormatter.ofPattern("YYYY년 MM월 dd일").withLocale(
                Locale.forLanguageTag("ko")))
            binding.dayView.text = day.format(DateTimeFormatter.ofPattern("E요일").withLocale(Locale.forLanguageTag("ko")))

            binding.submitButton.setOnClickListener {
                viewModel.insertMemo(
                    Memo(
                        emotionId = this.emotion.drawable,
                        date = binding.dateView.text.toString(),
                        day = binding.dayView.text.toString(),
                        content = binding.editTextView.text.toString()
                    )
                )

                val direction = WriteFragmentDirections.actionWriteFragmentToCalendarFragment()
                findNavController().navigate(direction)
            }
        }
    }
}