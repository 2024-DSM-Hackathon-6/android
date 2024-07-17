package com.dsm.hackathon.feature.dictionary

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
import com.dsm.hackathon.databinding.FragmentDictionaryBinding
import com.dsm.hackathon.feature.MainActivity
import com.dsm.hackathon.feature.home.model.InfoData
import com.dsm.hackathon.feature.home.model.InfoResponse
import com.dsm.hackathon.network.ApiProvider
import com.dsm.hackathon.network.HomeApi
import retrofit2.Call
import retrofit2.Response

class DictionaryFragment : Fragment(), DictionaryAdapter.DictionaryClickListener {
    private lateinit var binding: FragmentDictionaryBinding
    private val dicList = mutableListOf<InfoData>()
    private val adapter = DictionaryAdapter(dicList, this)
    private var name = "금융"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDictionaryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinnerDic.adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_text,
            resources.getStringArray(R.array.spinner_dictionary)
        )
        binding.spinnerDic.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                name = when(position) {
                    0 -> "금융"
                    1 -> "사회"
                    2 -> "공공"
                    else -> "금융"
                }
                getDicInfo()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.recyclerDic.adapter = adapter
        binding.recyclerDic.layoutManager = LinearLayoutManager(activity)
    }

    private fun getDicInfo() {
        dicList.clear()
        val apiProvider = ApiProvider.getInstance().create(HomeApi::class.java)
        apiProvider.getInfo(name).enqueue(object : retrofit2.Callback<InfoResponse> {
            override fun onResponse(call: Call<InfoResponse>, response: Response<InfoResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { setDicInfo(it.wordElements) }
                } else {
                    Toast.makeText(activity, "정보를 가져오는데 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<InfoResponse>, t: Throwable) {
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setDicInfo(responses: List<InfoData>) {
        for (response in responses) {
            dicList.add(response)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDicClicked(data: InfoData) {
        (activity as MainActivity).goDetailActivity(2, data.id)
    }
}