package com.fullstack405.bitcfinalprojectkotlin.templete.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fullstack405.bitcfinalprojectkotlin.templete.main.MainActivity
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.UserData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityLoginBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.main.AdminMainActivity
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_login)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // 자동로그인 데이터
        val sharedPreferences = getSharedPreferences("app_pref",Context.MODE_PRIVATE)
        val checkedUserId = sharedPreferences.getLong("userId",0)
        val checkedRole = sharedPreferences.getString("userRole",null)
        val checkedName = sharedPreferences.getString("userName",null)
        

        // 인텐트 데이터
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        val intentAdmin = Intent(this@LoginActivity, AdminMainActivity::class.java)


        // 자동로그인 로직
        if (checkedName != null) {
            // 자동로그인 시 권한 확인 후 메인화면 이동
            if(checkedRole == "ROLE_REGULAR"){
                // 정회원
                intent.putExtra("userId", checkedUserId)
                intent.putExtra("userName",checkedName)
                intent.putExtra("userPermission", checkedRole)
                startActivity(intent)
                finish() // 현재 액티비티 종료
            }
            else{
                // 정회원 외 관리자들
                intentAdmin.putExtra("userId", checkedUserId)
                intentAdmin.putExtra("userName",checkedName)
                intentAdmin.putExtra("userPermission", checkedRole)
                startActivity(intentAdmin)
                finish() // 현재 액티비티 종료
            }
        }


        
        // 로그인 버튼
        binding.btnLogin.setOnClickListener {

            // 유저 account , pw 받아서 디비에 확인 요청
            var ac = binding.inputId.text.toString()
            var pw = binding.inputPw.text.toString()
            var data = UserData(0,"name","phone","ROLE_REGULAR",ac,pw,"none")
            Client.retrofit.loginUser(data).enqueue(object:retrofit2.Callback<UserData>{
                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    Log.d("user response : ","${response.body()}")
                    var user = response.body() as UserData
                    if (user.role == "ROLE_REGULAR") { // 정회원
                        intent.putExtra("userId", user.userId)
                        intent.putExtra("userName", user.name)
                        intent.putExtra("userPermission", user.role)
                        saveUserData(user.userId,user.role,user.name)
                        startActivity(intent)
                    } else if (user.role == "ROLE_SECRETARY") { // 총무
                        intentAdmin.putExtra("userId", user.userId)
                        intentAdmin.putExtra("userName", user.name)
                        intentAdmin.putExtra("userPermission", user.role)
                        saveUserData(user.userId,user.role,user.name)
                        startActivity(intentAdmin)
                    } else if (user.role == "ROLE_PRESIDENT") { // 협회장
                        intentAdmin.putExtra("userId", user.userId)
                        intentAdmin.putExtra("userName", user.name)
                        intentAdmin.putExtra("userPermission", user.role)
                        saveUserData(user.userId,user.role,user.name)
                        startActivity(intentAdmin)
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "로그인에 실패하였습니다. 아이디와 비밀번호를 확인해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.inputId.setText("")
                        binding.inputPw.setText("")
                    }

                }
                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    Log.d("login error","${t.message}")
                    Toast.makeText(this@LoginActivity, "로그인에 실패하였습니다. 아이디와 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show()
                    binding.inputId.setText("")
                    binding.inputPw.setText("")
                }

            })

        } // btn login

        binding.btnSignup.setOnClickListener {
            var intentSignup = Intent(this,SignupActivity::class.java)
            startActivity(intentSignup)
        }
    } //onCreate

    // 앱에 정보 저장
    fun saveUserData(userId:Long, userRole: String,userName:String){
        val sharedPreferences = getSharedPreferences("app_pref", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()){
            putLong("userId",userId)
            putString("userRole",userRole)
            putString("userName",userName)
            apply()
        }
        Log.d("savaUserData","$sharedPreferences")
    }
}