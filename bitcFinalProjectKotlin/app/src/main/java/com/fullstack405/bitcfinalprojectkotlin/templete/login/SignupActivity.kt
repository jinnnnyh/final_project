package com.fullstack405.bitcfinalprojectkotlin.templete.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.UserData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
//    var intent = Intent(this@SignupActivity,LoginActivity::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_signup)
        val binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var account = ""
        var pw = ""
        var name = ""
        var phone = ""
        var dept = ""

        // 확인 누르면 아이디 중복확인 하고 전송 유무 정하기
//        binding.btnSubmit.setOnClickListener {
//            account = binding.editAccount.text.toString()
//            pw = binding.editPw.text.toString()
//            name = binding.editName.toString()
//            phone = binding.editPhone.toString()
//            dept = binding.editDept.toString()

//            Client.retrofit.CheckedId(account).enqueue(object:retrofit2.Callback<Boolean>{
//                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
//                    if(response.body()!!){
//                        Toast.makeText(this@SignupActivity,"이미 존재하는 아이디입니다. 다시 입력해주세요.",Toast.LENGTH_SHORT).show()
//                    }else{
//                        var user = UserData(0,name,phone,account,pw,dept,"준회원")
//                        insertUser(user)
//                    }
//                }
//
//                override fun onFailure(call: Call<Boolean>, t: Throwable) {
//                    Log.d("signup error","id checkError")
//                }
//
//            })
//
//        } // btnSubmit
//
        binding.btnCancle.setOnClickListener {
            finish()
        }
//
    }//onCreate

    fun insertUser(data:UserData){
        Client.retrofit.insetUser(data).enqueue(object:retrofit2.Callback<UserData>{
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                Toast.makeText(this@SignupActivity,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.d("signup error","insert Error")
            }

        })
    }// insertUser()
}