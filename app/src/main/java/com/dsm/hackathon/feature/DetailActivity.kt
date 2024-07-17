package com.dsm.hackathon.feature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dsm.hackathon.R
import com.dsm.hackathon.databinding.ActivityDetailBinding
import com.dsm.hackathon.feature.home.HomeDetailFragment
import com.dsm.hackathon.feature.home.InquireFragment
import com.dsm.hackathon.feature.home.model.HomeData

class DetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent()
        val index = intent.getIntExtra("index", 0)
        val id = intent.getLongExtra("id", 0)
        val title = intent.getStringExtra("title") ?: ""
        val content = intent.getStringExtra("content") ?: ""

        when(index) {
            1 -> setFrag(HomeDetailFragment(HomeData(id, title, content)))
        }


        binding.ivDetailBack.setOnClickListener {
            finish()
        }
        binding.tvDetailBack.setOnClickListener {
            finish()
        }
    }

    fun setFrag(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_detail, fragment).commit()
    }

    fun activityFinish() {
        finish()
    }
}