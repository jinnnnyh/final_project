package com.fullstack405.bitcfinalprojectkotlin.templete.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.client.Client
import com.fullstack405.bitcfinalprojectkotlin.data.UpdateData
import com.fullstack405.bitcfinalprojectkotlin.data.UserData
import com.fullstack405.bitcfinalprojectkotlin.databinding.ActivityEditUserInfoBinding
import com.fullstack405.bitcfinalprojectkotlin.templete.login.LoginActivity
import retrofit2.Call
import retrofit2.Response

class EditUserInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_edit_user_info)
        val binding = ActivityEditUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 뒤로가기
        binding.btnBack.setOnClickListener {
            finish()
        }

        // 인텐트로 받은 유저id로 유저 정보 추출해서 화면에 뿌려야함
        var userId = intent.getLongExtra("userId", 0)



        // 비밀번호, 폰번호, 소속만 수정 가능, 나머지는 비활성화
        binding.editName.isEnabled = false
        binding.editAccount.isEnabled = false

        lateinit var user: UserData
        // db 연결버전
        Client.retrofit.findUserId(userId).enqueue(object:retrofit2.Callback<UserData>{
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                user = response.body() as UserData

                binding.run {
                    editAccount.setText(user.userAccount)
                    editPw.setText(user.password)
                    editName.setText(user.name)
                    editPhone.setText(user.userPhone)
                    editCompany.setText(user.userDepart)
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.d("userInfoEdit error","userInfoEdit error")
            }
        })

        // 수정 버튼
        binding.btnSubmit.setOnClickListener {
            var pw = binding.editPw.text.toString()
            var phone = binding.editPhone.text.toString()
            var company = binding.editCompany.text.toString()

            // db에 보낼 데이터 수정 데이터 타입 다르게 만들기
            var data = UpdateData(pw,phone,company)
            Client.retrofit.updateUser(userId,data).enqueue(object:retrofit2.Callback<Void>{
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Toast.makeText(this@EditUserInfoActivity,"수정이 완료되었습니다",Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(this@EditUserInfoActivity,"수정 실패",Toast.LENGTH_SHORT).show()
                }
            })
        }// btn_submit

            // 취소 버튼
        binding.btnCancle.setOnClickListener {
            finish()
        }

        // 회원탈퇴
        binding.deleteUser.setOnClickListener {
            AlertDialog.Builder(this).run{
                setMessage("회원을 탈퇴하시겠습니까?")
                setPositiveButton("확인",object:DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Client.retrofit.deleteUser(userId).enqueue(object:retrofit2.Callback<Void>{
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                val intent = Intent(this@EditUserInfoActivity, LoginActivity::class.java)
                                startActivity(intent)
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Toast.makeText(this@EditUserInfoActivity,"회원탈퇴 실패",Toast.LENGTH_SHORT).show()
                            }
                        }) // retrofit
                    }
                }) // positive btn
                setNegativeButton("취소",null)
                show()
            } // dialog
        } // deleteUser




    } // onCreate
}
