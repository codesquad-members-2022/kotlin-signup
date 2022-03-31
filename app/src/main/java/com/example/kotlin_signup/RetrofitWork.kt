package com.example.kotlin_signup

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class RetrofitWork(private val userInfo: SignUpRequestBody) {
    fun work() {
        val service = RetrofitAPI.emgMedService

        // JSONObject 을 이용해서 JSONObject 를 만든다.
        val jsonObject = JSONObject()
        jsonObject.put("id", userInfo.userId)
        jsonObject.put("password", userInfo.password)

        // JSONObject 를 String 타입으로 변환
        val jsonObjectString = jsonObject.toString()

        // RequestBody 생성
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // POST request 를 보내고 response 를 받는다.
            val response = service.addUser(requestBody)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("회원가입 성공", "$result")
                } else {
                    Log.d("회원가입 실패", response.code().toString())
                }
            }
        }
    }
}