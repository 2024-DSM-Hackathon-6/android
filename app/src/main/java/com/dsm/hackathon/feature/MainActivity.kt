package com.dsm.hackathon.feature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dsm.hackathon.R
import com.dsm.hackathon.databinding.ActivityMainBinding
import com.dsm.hackathon.feature.home.HomeFragment
import com.dsm.hackathon.feature.home.model.HomeData

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFrag(1)
    }

    private fun setFrag(fragNum: Int): Boolean {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum) {
            1 -> ft.replace(R.id.frame_main, HomeFragment()).commit()
        }
        return true
    }

    fun goDetailActivity(index: Int, id: Long, title: String, content: String) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("index", index)
        intent.putExtra("id", id)
        intent.putExtra("title", title)
        intent.putExtra("content", content)
        startActivity(intent)
    }
}