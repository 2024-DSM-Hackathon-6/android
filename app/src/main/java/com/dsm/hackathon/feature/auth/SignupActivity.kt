package com.dsm.hackathon.feature.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dsm.hackathon.databinding.ActivitySignupBinding
import com.dsm.hackathon.feature.auth.model.SignupRequest
import com.dsm.hackathon.feature.auth.model.SignupResponse
import com.dsm.hackathon.network.ApiProvider
import com.dsm.hackathon.network.AuthApi
import com.dsm.hackathon.utill.userIdentifier
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            val id = binding.editSignupId.text.toString()
            val pw = binding.editSignupPw.text.toString()
            val pwCheck = binding.editSignupPwCheck.text.toString()
            if (id == "" || pw == "" || pwCheck == "") {
                Toast.makeText(this, "입력창을 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            } else if (checkPw(pw, pwCheck)) {
                signUp(id, pw)
            }
        }

        binding.tvSignupLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun checkPw(pw: String, pwCheck: String): Boolean {
        if (pw == pwCheck) {
            return true
        }
        Toast.makeText(this, "비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
        return false
    }

    private fun signUp(id: String, pw: String) {
        val apiProvider = ApiProvider.getInstance().create(AuthApi::class.java)
        apiProvider.signup(SignupRequest(id, pw)).enqueue(object : Callback<SignupResponse> {
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful) {
                    userIdentifier = response.body()?.userIdentifier ?: ""
                    Toast.makeText(this@SignupActivity, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SignupActivity, "회원가입에 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "서버 연결 실패", Toast.LENGTH_SHORT).show()
            }
        })
    }
}