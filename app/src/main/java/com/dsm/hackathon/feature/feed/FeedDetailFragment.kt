package com.dsm.hackathon.feature.feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dsm.hackathon.R
import com.dsm.hackathon.databinding.FragmentFeedDetailBinding
import com.dsm.hackathon.feature.DetailActivity
import com.dsm.hackathon.feature.feed.model.FeedData
import com.dsm.hackathon.network.ApiProvider
import com.dsm.hackathon.network.FeedApi
import com.dsm.hackathon.utill.userIdentifier
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedDetailFragment(private val feedId: Long) : Fragment() {
    private lateinit var binding: FragmentFeedDetailBinding
    private var menuVisible = false
    private var isLiked = false

    private val apiProvider = ApiProvider.getInstance().create(FeedApi::class.java)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInfo()

        binding.ivFeedDetailMenu.setOnClickListener {
            if (menuVisible) {
                binding.layFeedDetailMenu.visibility = View.GONE
                menuVisible = false
            } else {
                binding.layFeedDetailMenu.visibility = View.VISIBLE
                menuVisible = true
            }
        }
        binding.root.setOnClickListener {
            if (menuVisible) {
                binding.layFeedDetailMenu.visibility = View.GONE
                menuVisible = false
            }
        }
        binding.layFeedDetailLike.setOnClickListener {
            if (isLiked) {
                cancelLike()
            } else {
                like()
            }
            isLiked = !isLiked
        }
        binding.tvFeedDetailDelete.setOnClickListener {
            feedDelete()
        }
        binding.tvFeedDetailModify.setOnClickListener {
            (activity as DetailActivity).setFrag(CreateFeedFragment(feedId, binding.tvFeedDetailTitle.text.toString(), binding.tvFeedDetailContent.text.toString()))
        }
    }

    private fun getInfo() {
        apiProvider.getDetailFeed(feedId, userIdentifier).enqueue(object : Callback<FeedData> {
            override fun onResponse(call: Call<FeedData>, response: Response<FeedData>) {
                if (response.isSuccessful) {
                    response.body()?.let { setInfo(it) }
                } else {
                    Toast.makeText(activity, "정보를 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<FeedData>, t: Throwable) {
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setInfo(response: FeedData) {
        binding.tvFeedDetailTitle.text = response.title
        binding.tvFeedDetailContent.text = response.content
        binding.tvFeedDetailWriter.text = response.userName
        binding.tvFeedDetailDate.text = response.createDate
        binding.tvFeedDetailCount.text = response.likeCount.toString()
        isLiked = response.isLiked

        if (response.isLiked) {
            context?.let { binding.ivFeedDetailLike.setColorFilter(it.getColor(R.color.main300)) }
        } else {
            context?.let { binding.ivFeedDetailLike.setColorFilter(it.getColor(R.color.gray600)) }
        }

        if (response.isMine) {
            binding.ivFeedDetailMenu.visibility = View.VISIBLE
        }
    }

    private fun cancelLike() {
        apiProvider.feedLikeCancel(feedId, userIdentifier).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    context?.let { binding.ivFeedDetailLike.setColorFilter(it.getColor(R.color.gray600)) }
                    binding.tvFeedDetailCount.text = (binding.tvFeedDetailCount.text.toString().toInt() - 1).toString()
                } else {
                    Toast.makeText(activity, "실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun like() {
        apiProvider.feedLike(feedId, userIdentifier).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    context?.let { binding.ivFeedDetailLike.setColorFilter(it.getColor(R.color.main300)) }
                    binding.tvFeedDetailCount.text = (binding.tvFeedDetailCount.text.toString().toInt() + 1).toString()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun feedDelete() {
        apiProvider.deleteFeed(feedId, userIdentifier).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(activity, "게시글이 삭제되었습니다", Toast.LENGTH_SHORT).show()
                    (activity as DetailActivity).deleteActivity()
                } else {
                    Toast.makeText(activity, "게시글 삭제에 실패하였습니다", Toast.LENGTH_SHORT).show()
                    Log.d("server", response.code().toString())
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
}