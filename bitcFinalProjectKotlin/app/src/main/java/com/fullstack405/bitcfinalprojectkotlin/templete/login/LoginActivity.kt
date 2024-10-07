package com.fullstack405.bitcfinalprojectkotlin.templete.login

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
import com.fullstack405.bitcfinalprojectkotlin.templete.attend.AttendListActivity
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

        val memberList = mutableListOf<UserData>()
        memberList.add(UserData(0,"박성호","010-1111-1111","정회원","test1","test","A회사"))
        memberList.add(UserData(0,"명재현","010-1111-1111","준회원","test2","test","B회사"))
        memberList.add(UserData(0,"김운학","010-1111-1111","협회장","test3","test","C회사"))
        memberList.add(UserData(0,"한태산","010-1111-1111","총무","admin","admin","D회사"))

//        var userList = mutableListOf<UserData>()
//        // 로그인 화면 켜지면 유저 리스트 불러와서 저장
//        Client.retrofit.findUserList().enqueue(object:retrofit2.Callback<List<UserData>>{
//            override fun onResponse(call: Call<List<UserData>>,response: Response<List<UserData>>) {
//                userList = response.body() as MutableList<UserData>
//            }
//
//            override fun onFailure(call: Call<List<UserData>>, t: Throwable) {
//                Log.d("login error","userList load error")
//            }
//
//        })

        // 로그인 정보 저장을 세션에 하는 방법 뭐 이런거 써야하는ㄱ ㅓ아닌가 ?? 
        // 일단은 회원 정보 확인하고 메인으로 넘어가기 완료
        var id =""
        var pw = ""
        var toastId =""
        var toastPw =""
        var user:UserData? =null
        var admin:UserData? =null

        var intent = Intent(this@LoginActivity, MainActivity::class.java)
        var intentAdmin = Intent(this@LoginActivity, AdminMainActivity::class.java)

        binding.btnLogin.setOnClickListener {
            id =binding.inputId.text.toString()
            pw= binding.inputPw.text.toString()

            for(item in memberList){ // db 연결하면 여기 userList로 바꾸기
                if(item.userAccount.equals(id)){
                    if(item.userPw.equals(pw)){
                        if(item.userPermission == "정회원"){
                            user = item
                            toastId=""
                            toastPw=""
                            break
                        }
                        else if(item.userPermission == "관리자"){
                            admin=item
                            toastId=""
                            toastPw=""
                            break
                        }
                        else if(item.userPermission == "협회장"){
                            admin=item
                            toastId=""
                            toastPw=""
                            break
                        }
                        else if(item.userPermission == "준회원"){
                            toastId="wait"
                            toastPw="wait"
                            break
                        }

                    }else{
                        toastId= ""
                        toastPw = "pw error"
                        break

                    }
                }else{
                    toastId = "id error"

                }
            } // for


            if(toastId.equals("") && toastPw.equals("pw error")){
                Toast.makeText(this,"비밀번호가 틀렸습니다.",Toast.LENGTH_SHORT).show()
                binding.inputId.setText("")
                binding.inputPw.setText("")
                toastId=""
                toastPw=""
            }
            else if(toastId.equals("id error") && toastPw.equals("")){
                Toast.makeText(this,"아이디가 없습니다.",Toast.LENGTH_SHORT).show()
                binding.inputId.setText("")
                binding.inputPw.setText("")
                toastId=""
                toastPw=""
            }
            else if(toastId.equals("wait") && toastPw.equals("wait")){
                Toast.makeText(this,"승인 대기",Toast.LENGTH_SHORT).show()
                binding.inputId.setText("")
                binding.inputPw.setText("")
                toastId=""
                toastPw=""
            }


            if(user != null && admin == null){
                intent.putExtra("userId", user!!.userId)
                intent.putExtra("userName",user!!.userName)
                intent.putExtra("userPermission",user!!.userPermission)
                startActivity(intent)
                user = null
            }
            else if(user == null && admin != null){
                intentAdmin.putExtra("userId", admin!!.userId)
                intentAdmin.putExtra("userName",admin!!.userName)
                intentAdmin.putExtra("userPermission",admin!!.userPermission)
                startActivity(intentAdmin)
                admin = null
            }
        } // setOnClickListener

        binding.btnSignup.setOnClickListener {
            var intentSignup = Intent(this,SignupActivity::class.java)
            startActivity(intentSignup)
        }
    }
}