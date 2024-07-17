package com.dsm.hackathon.feature.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dsm.hackathon.databinding.FragmentHomeDetailBinding
import com.dsm.hackathon.feature.CustomDialog
import com.dsm.hackathon.feature.CustomDialogInterface
import com.dsm.hackathon.feature.DetailActivity
import com.dsm.hackathon.feature.home.model.InfoData
import com.dsm.hackathon.feature.home.model.ModifyRequest
import com.dsm.hackathon.network.ApiProvider
import com.dsm.hackathon.network.HomeApi
import com.dsm.hackathon.utill.userIdentifier
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeDetailFragment(private val infoId: Long) : Fragment(), CustomDialogInterface {
    private lateinit var binding: FragmentHomeDetailBinding
    private var menuClicked: Boolean = false

    private var infoTitle = ""
    private var infoContent = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getInfo()

        binding.root.setOnClickListener {
            if (menuClicked) {
                binding.layHomeDetailMenu.visibility = View.GONE
                menuClicked = false
            }
        }
        binding.ivHomeDetailMenu.setOnClickListener {
            if (menuClicked) {
                binding.layHomeDetailMenu.visibility = View.GONE
                menuClicked = false
            } else {
                binding.layHomeDetailMenu.visibility = View.VISIBLE
                menuClicked = true
            }
        }

        binding.tvHomeDetailModify.setOnClickListener {
            requestModify()
        }
        binding.tvHomeDetailInquire.setOnClickListener {
            inquire()
        }
    }
    private fun getInfo() {
        val apiProvider = ApiProvider.getInstance().create(HomeApi::class.java)
        apiProvider.getDetailInfo(infoId).enqueue(object : Callback<InfoData> {
            override fun onResponse(call: Call<InfoData>, response: Response<InfoData>) {
                Log.d("server", response.code().toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        infoTitle = responseBody.title
                        infoContent = responseBody.content
                        setInfo()
                    }
                } else {
                    Toast.makeText(activity, "정보를 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<InfoData>, t: Throwable) {
                Log.d("server", t.message.toString())
                Toast.makeText(activity, "서버 연결 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setInfo() {
        binding.tvHomeDetailTitle.text = infoTitle
        binding.tvHomeDetailContent.text = infoContent
    }

    private fun requestModify() {
        val dialog = CustomDialog(this)
        activity?.supportFragmentManager?.let { dialog.show(it, "") }
    }
    private fun inquire() {
        (activity as DetailActivity).setFrag(InquireFragment(InfoData(infoId, infoTitle, infoContent)))
    }

    override fun onYesButtonClick(content: String) {
        val apiProvider = ApiProvider.getInstance().create(HomeApi::class.java)
        apiProvider.requestModify(userIdentifier, ModifyRequest(infoId, content)).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(activity, "수정 요청이 전송되었습니다", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "수정 요청 전송에 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
}