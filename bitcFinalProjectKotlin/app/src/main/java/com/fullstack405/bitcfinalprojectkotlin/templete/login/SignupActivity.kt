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
import com.fullstack405.bitcfinalprojectkotlin.data.InsertUserData
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
        binding.btnSubmit.setOnClickListener {
            account = binding.editAccount.text.toString()
            pw = binding.editPw.text.toString()
            name = binding.editName.text.toString()
            phone = binding.editPhone.text.toString()
            dept = binding.editDept.text.toString()

//            Log.d("user signup","${account},${pw},${name},${phone},${dept}")

            // 준회원으로 등록됨
            var user = InsertUserData(name,phone,account,pw,dept)
//             중복확인 없이 회원가입 되는지부터 확인하기
//            insertUser(user)
            Client.retrofit.CheckedId(account).enqueue(object:retrofit2.Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    // 아이디 존재 T / 없음 F
                    if(response.body() == false){
                        Toast.makeText(this@SignupActivity,"회원가입에 성공하였습니다.",Toast.LENGTH_SHORT).show()
                        insertUser(user)
                    }
                    else{
                        Toast.makeText(this@SignupActivity,"이미 존재하는 아이디입니다. 다시 입력해주세요.",Toast.LENGTH_SHORT).show()
                    }

//                  var user = InsertUserData(0,name,phone,account,pw,dept,"준회원")
//                  insertUser(user)
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("signup error","id checkError")
                }

            }) //checkedId

        } // btnSubmit

        binding.btnCancle.setOnClickListener {
            finish()
        }
//
    }//onCreate

    fun insertUser(data:InsertUserData){
        Client.retrofit.insertUser(data).enqueue(object:retrofit2.Callback<InsertUserData>{
            override fun onResponse(call: Call<InsertUserData>, response: Response<InsertUserData>) {
                Toast.makeText(this@SignupActivity,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<InsertUserData>, t: Throwable) {
                Toast.makeText(this@SignupActivity,"회원가입에 실패하였습니다.",Toast.LENGTH_SHORT).show()
                
            }

        })
    }// insertUser()
}