package com.dsm.hackathon.feature.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dsm.hackathon.databinding.FragmentInquireBinding
import com.dsm.hackathon.feature.DetailActivity
import com.dsm.hackathon.feature.home.model.InfoData
import com.dsm.hackathon.feature.home.model.ModifyRequest
import com.dsm.hackathon.network.ApiProvider
import com.dsm.hackathon.network.HomeApi
import com.dsm.hackathon.utill.userIdentifier
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val apiProvider = ApiProvider.getInstance().create(HomeApi::class.java)
        apiProvider.requestInquiry(userIdentifier, ModifyRequest(data.id, content)).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(activity, "전성에 성공하였습니다", Toast.LENGTH_SHORT).show()
                    (activity as DetailActivity).setFrag(HomeDetailFragment(data.id))
                } else {
                    Log.d("server", response.code().toString())
                    Toast.makeText(activity, "전송에 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, "서버 통신 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
}