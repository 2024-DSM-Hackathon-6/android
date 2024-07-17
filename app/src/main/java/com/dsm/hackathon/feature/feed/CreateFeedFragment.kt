package com.dsm.hackathon.feature.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dsm.hackathon.R
import com.dsm.hackathon.databinding.FragmentCreateFeedBinding
import com.dsm.hackathon.feature.DetailActivity
import com.dsm.hackathon.feature.MainActivity
import com.dsm.hackathon.feature.feed.model.CreateFeedRequest
import com.dsm.hackathon.network.ApiProvider
import com.dsm.hackathon.network.FeedApi
import com.dsm.hackathon.utill.userIdentifier
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateFeedFragment : Fragment() {
    private lateinit var binding: FragmentCreateFeedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateFeedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreate.setOnClickListener {
            val title = binding.editCreateTitle.text.toString()
            val content = binding.editCreateContent.text.toString()
            if (title == "" || content == "") {
                Toast.makeText(activity, "입력을 완료해주세요", Toast.LENGTH_SHORT).show()
            } else {
                createFeed(title, content)
            }
        }
    }

    private fun createFeed(title: String, content: String) {
        val apiProvider = ApiProvider.getInstance().create(FeedApi::class.java)
        apiProvider.createFeed(userIdentifier, CreateFeedRequest(title, content)).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(activity, "등록이 완료되었습니다", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "등록에 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
                (activity as DetailActivity).deleteActivity()
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, "서버 연결 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
}