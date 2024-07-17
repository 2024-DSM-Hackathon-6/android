package com.dsm.hackathon.feature.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dsm.hackathon.feature.MainActivity
import com.dsm.hackathon.databinding.ActivityLoginBinding
import com.dsm.hackathon.feature.auth.model.LoginRequest
import com.dsm.hackathon.feature.auth.model.LoginResponse
import com.dsm.hackathon.network.ApiProvider
import com.dsm.hackathon.network.AuthApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val id = binding.editLoginId.text.toString()
            val pw = binding.editLoginPw.text.toString()
            if (id == "" || pw == "") {
                Toast.makeText(this, "아이디나 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                login(id, pw)
            }
        }

        binding.tvLoginSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(id: String, pw: String) {
        val apiProvider = ApiProvider.getInstance().create(AuthApi::class.java)
        apiProvider.login(LoginRequest(id, pw)).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "아이디나 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "서버 연동 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
}