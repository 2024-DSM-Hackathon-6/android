package com.dsm.hackathon.feature.feed

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dsm.hackathon.R
import com.dsm.hackathon.databinding.FragmentFeedBinding
import com.dsm.hackathon.feature.DetailActivity
import com.dsm.hackathon.feature.MainActivity
import com.dsm.hackathon.feature.feed.model.FeedData
import com.dsm.hackathon.feature.feed.model.FeedResponse
import com.dsm.hackathon.network.ApiProvider
import com.dsm.hackathon.network.FeedApi
import com.dsm.hackathon.utill.userIdentifier
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedFragment : Fragment(), FeedAdapter.FeedClickListener {
    private lateinit var binding: FragmentFeedBinding
    private var orderSelect = "DATE"
    private val feedList = mutableListOf<FeedData>()
    private val adapter = FeedAdapter(feedList, this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvFeedDay.background = orderSelected(binding.tvFeedDay)
        binding.tvFeedPopular.background = orderUnselected(binding.tvFeedPopular)

        binding.recyclerFeed.adapter = adapter
        binding.recyclerFeed.layoutManager = LinearLayoutManager(activity)

        getFeedInfo()
        adapter.notifyDataSetChanged()

        binding.tvFeedDay.setOnClickListener {
           if (orderSelect == "POPULAR") {
                binding.tvFeedDay.background = orderSelected(binding.tvFeedDay)
                binding.tvFeedPopular.background = orderUnselected(binding.tvFeedPopular)
                orderSelect = "DATE"
                getFeedInfo()
            }
        }
        binding.tvFeedPopular.setOnClickListener {
            if (orderSelect == "DATE") {
                Log.d("click", "인기순 클릭")
                binding.tvFeedPopular.background = orderSelected(binding.tvFeedPopular)
                binding.tvFeedDay.background = orderUnselected(binding.tvFeedDay)
                orderSelect = "POPULAR"
                getFeedInfo()
            }
        }

        binding.ivFeedCreate.setOnClickListener {
            (activity as MainActivity).goDetailActivity(3, 0)
        }
    }

    private fun orderSelected(textView: TextView): GradientDrawable {
        textView.setTextColor(resources.getColor(R.color.white))

        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(resources.getColor(R.color.main400))
        gradientDrawable.setStroke(2, resources.getColor(R.color.main400))
        gradientDrawable.cornerRadius = 30f

        textView.isClickable = true
        return gradientDrawable
    }
    private fun orderUnselected(textView: TextView): GradientDrawable {
        textView.setTextColor(resources.getColor(R.color.main400))

        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(resources.getColor(R.color.white))
        gradientDrawable.setStroke(2, resources.getColor(R.color.main400))
        gradientDrawable.cornerRadius = 30f

        textView.isClickable = true
        return gradientDrawable
    }
    private fun getFeedInfo() {
        feedList.clear()
        val apiProvider = ApiProvider.getInstance().create(FeedApi::class.java)
        apiProvider.getFeeds(orderSelect, userIdentifier).enqueue(object : Callback<FeedResponse> {
            override fun onResponse(call: Call<FeedResponse>, response: Response<FeedResponse>) {
                if (response.isSuccessful) {
                    Log.d("server", response.body().toString())
                    response.body()?.let { setFeedInfo(it.feedElements) }
                } else {
                    Toast.makeText(activity, "게시글 정보를 가져오는데 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FeedResponse>, t: Throwable) {
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setFeedInfo(responses: List<FeedData>) {
        for (response in responses) {
            feedList.add(response)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onFeedClicked(feedId: Long) {
        (activity as MainActivity).goDetailActivity(4, feedId)
    }

    override fun onLikeClicked(feedId: Long, isLiked: Boolean) {
        if (isLiked) {
            feedLikeCancel(feedId)
        } else {
            feedLike(feedId)
        }
    }

    private fun feedLike(feedId: Long) {
        val apiProvider = ApiProvider.getInstance().create(FeedApi::class.java)
        apiProvider.feedLike(feedId, userIdentifier).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("server", "좋아요 성공")
                } else {
                    Toast.makeText(activity, "실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun feedLikeCancel(feedId: Long) {
        val apiProvider = ApiProvider.getInstance().create(FeedApi::class.java)
        apiProvider.feedLikeCancel(feedId, userIdentifier).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("server", "좋아요 취소 성공")
                } else {
                    Toast.makeText(activity, "실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
}