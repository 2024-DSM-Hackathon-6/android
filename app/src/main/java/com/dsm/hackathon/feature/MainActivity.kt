package com.dsm.hackathon.feature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dsm.hackathon.R
import com.dsm.hackathon.databinding.ActivityMainBinding
import com.dsm.hackathon.feature.dictionary.DictionaryFragment
import com.dsm.hackathon.feature.feed.FeedFragment
import com.dsm.hackathon.feature.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFrag(HomeFragment())

        binding.bottomNavigationMain.setOnItemSelectedListener { item ->
            setFrag(
                when (item.itemId) {
                    R.id.menu_home -> HomeFragment()
                    R.id.menu_dictionary -> DictionaryFragment()
                    R.id.menu_notice -> FeedFragment()
                    else -> HomeFragment()
                }
            )
            true
        }
    }

    private fun setFrag(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_main, fragment).commit()
    }

    fun goDetailActivity(index: Int, id: Long) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
            putExtra("index", index)
            putExtra("id", id)
        }
        startActivity(intent)
    }
}