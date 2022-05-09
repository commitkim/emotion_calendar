package com.example.emotioncalendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emotioncalendar.adapter.MemoListAdapter
import com.example.emotioncalendar.databinding.FragmentMemoListBinding
import com.example.emotioncalendar.viewmodel.MemoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MemoListFragment : Fragment() {

    private lateinit var binding: FragmentMemoListBinding
    private val viewModel: MemoListViewModel by viewModels()

    @Inject
    lateinit var memoListAdapter: MemoListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMemoListBinding.inflate(inflater, container, false)
        init()
        subscribeUI()
        return binding.root
    }

    private fun init() {
        with(binding.recyclerView) {
            adapter = memoListAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        }
    }

    private fun subscribeUI() {
        viewModel.memos.observe(viewLifecycleOwner) { memos ->
            memoListAdapter.submitList(memos)
        }
    }
}