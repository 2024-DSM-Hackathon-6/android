package com.dsm.hackathon.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsm.hackathon.R
import com.dsm.hackathon.databinding.FragmentHomeBinding
import com.dsm.hackathon.feature.MainActivity
import com.dsm.hackathon.feature.home.model.HomeData

class HomeFragment : Fragment(), HomeAdapter.HomeClickListener {
    private lateinit var binding: FragmentHomeBinding
    private val homeList = mutableListOf<HomeData>()
    private val adapter = HomeAdapter(homeList, this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeList.clear()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeList.add(HomeData(1, "제목1", "내용1"))
        homeList.add(HomeData(2, "제목2", "내용2"))
        homeList.add(HomeData(3, "제목3", "내용3"))
        homeList.add(HomeData(4, "제목4", "내용4"))
        homeList.add(HomeData(5, "제목5", "내용5"))

        binding.spinnerHome.adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_text,
            resources.getStringArray(R.array.spinner_home)
        )

        binding.recyclerHome.adapter = adapter
        binding.recyclerHome.layoutManager = LinearLayoutManager(activity)
    }

    override fun onHomeClicked(data: HomeData) {
        (activity as MainActivity).goDetailActivity(1, data.id, data.title, data.content)
    }
}