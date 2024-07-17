package com.dsm.hackathon.feature.dictionary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dsm.hackathon.R
import com.dsm.hackathon.databinding.FragmentDictionaryDetailBinding
import com.dsm.hackathon.feature.home.model.InfoData
import com.dsm.hackathon.network.ApiProvider
import com.dsm.hackathon.network.HomeApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DictionaryDetailFragment(private val infoId: Long) : Fragment() {
    private lateinit var binding: FragmentDictionaryDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDictionaryDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getInfo()
    }

    private fun getInfo() {
        val apiProvider = ApiProvider.getInstance().create(HomeApi::class.java)
        apiProvider.getDetailInfo(infoId).enqueue(object : Callback<InfoData> {
            override fun onResponse(call: Call<InfoData>, response: Response<InfoData>) {
                if (response.isSuccessful) {
                    response.body()?.let { setInfo(it) }
                } else {
                    Toast.makeText(activity, "정보를 가져오는데 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<InfoData>, t: Throwable) {
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setInfo(response: InfoData) {
        binding.tvDicDetailTitle.text = response.title
        binding.tvDicDetailContent.text = response.content
    }
}