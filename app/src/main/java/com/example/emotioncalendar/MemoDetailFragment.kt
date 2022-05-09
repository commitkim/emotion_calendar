package com.example.emotioncalendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.emotioncalendar.databinding.FragmentMemoDetailBinding
import com.example.emotioncalendar.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoDetailFragment : Fragment() {

    private val args: MemoDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentMemoDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemoDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
        viewModel.getMemo(args.date)
    }

    private fun subscribeUi() {
        viewModel.currentMemo.observe(viewLifecycleOwner) { memo ->
            Log.e("Jungbong", memo?.toString() ?: "null")
            binding.imageView.background = ContextCompat.getDrawable(binding.imageView.context, memo.emotionId)
            binding.dateView.text = memo.date
            binding.dayView.text = memo.day
            binding.contentView.text = memo.content
        }
    }

}