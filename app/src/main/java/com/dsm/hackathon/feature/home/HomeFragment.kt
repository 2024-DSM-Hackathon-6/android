package com.dsm.hackathon.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsm.hackathon.R
import com.dsm.hackathon.databinding.FragmentHomeBinding
import com.dsm.hackathon.feature.MainActivity
import com.dsm.hackathon.feature.home.model.InfoData
import com.dsm.hackathon.feature.home.model.InfoResponse
import com.dsm.hackathon.network.ApiProvider
import com.dsm.hackathon.network.HomeApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), HomeAdapter.HomeClickListener {
    private lateinit var binding: FragmentHomeBinding
    private val homeList = mutableListOf<InfoData>()
    private val adapter = HomeAdapter(homeList, this)
    private var name = "경제"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinnerHome.adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_text,
            resources.getStringArray(R.array.spinner_home)
        )
        binding.spinnerHome.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                name = when(position) {
                    0 -> "경제"
                    1 -> "법률"
                    2 -> "예절"
                    else -> "경제"
                }
                getInfo()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.recyclerHome.adapter = adapter
        binding.recyclerHome.layoutManager = LinearLayoutManager(activity)
    }

    private fun getInfo() {
        homeList.clear()
        val apiProvider = ApiProvider.getInstance().create(HomeApi::class.java)
        apiProvider.getInfo(name).enqueue(object : Callback<InfoResponse> {
            override fun onResponse(call: Call<InfoResponse>, response: Response<InfoResponse>) {
                if (response.isSuccessful) {
                    val resBody = response.body()
                    if (resBody != null) {
                        setInfo(resBody.wordElements)
                    }
                } else {
                    Toast.makeText(activity, "정보를 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<InfoResponse>, t: Throwable) {
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setInfo(responses: List<InfoData>) {
        homeList.addAll(responses)
        adapter.notifyDataSetChanged()
    }

    override fun onHomeClicked(data: InfoData) {
        (activity as MainActivity).goDetailActivity(1, data.id)
    }
}