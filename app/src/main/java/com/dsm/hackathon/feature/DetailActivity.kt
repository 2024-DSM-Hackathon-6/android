package com.dsm.hackathon.feature

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dsm.hackathon.R
import com.dsm.hackathon.databinding.ActivityDetailBinding
import com.dsm.hackathon.feature.dictionary.DictionaryDetailFragment
import com.dsm.hackathon.feature.feed.CreateFeedFragment
import com.dsm.hackathon.feature.feed.FeedDetailFragment
import com.dsm.hackathon.feature.home.HomeDetailFragment

class DetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val index = intent.getIntExtra("index", 0)
        val id = intent.getLongExtra("id", 0)

        when (index) {
            1 -> setFrag(HomeDetailFragment(id))
            2 -> setFrag(DictionaryDetailFragment(id))
            3 -> setFrag(CreateFeedFragment())
            4 -> setFrag(FeedDetailFragment(id))
            else -> Log.d("when", "else 실행")
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
    fun deleteActivity() {
        finish()
    }
}