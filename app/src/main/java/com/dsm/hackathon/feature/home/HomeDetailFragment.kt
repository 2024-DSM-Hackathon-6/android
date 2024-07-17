package com.dsm.hackathon.feature.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dsm.hackathon.R
import com.dsm.hackathon.databinding.FragmentHomeDetailBinding
import com.dsm.hackathon.feature.CustomDialog
import com.dsm.hackathon.feature.CustomDialogInterface
import com.dsm.hackathon.feature.DetailActivity
import com.dsm.hackathon.feature.home.model.HomeData
import com.dsm.hackathon.network.ApiProvider
import com.dsm.hackathon.network.HomeApi

class HomeDetailFragment(private val data: HomeData) : Fragment(), CustomDialogInterface {
    private lateinit var binding: FragmentHomeDetailBinding
    private var menuClicked: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvHomeDetailTitle.text = data.title
        binding.tvHomeDetailContent.text = data.content

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

    private fun requestModify() {
        val dialog = CustomDialog(this)
        activity?.supportFragmentManager?.let { dialog.show(it, "") }
    }
    private fun inquire() {
        (activity as DetailActivity).setFrag(InquireFragment(data))
    }

    override fun onYesButtonClick(content: String) {
        val apiProvider = ApiProvider.getInstance().create(HomeApi::class.java)

    }
}