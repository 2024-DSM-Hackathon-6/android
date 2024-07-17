package com.dsm.hackathon.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dsm.hackathon.databinding.FragmentInquireBinding
import com.dsm.hackathon.feature.home.model.InfoData

class InquireFragment(private val data: InfoData) : Fragment() {
    private lateinit var binding: FragmentInquireBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInquireBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvInquireTitle.text = data.title
        binding.tvInquireContent.text = data.content

        binding.btnInquire.setOnClickListener {
            val inquireContent = binding.editInquire.text.toString()
            inquire(inquireContent)
        }
    }

    private fun inquire(content: String) {
        // TODO: 문의하기
        // TODO: 이전 화면으로 이동
    }
}