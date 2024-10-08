package com.fullstack405.bitcfinalprojectkotlin.fragments.attend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.fullstack405.bitcfinalprojectkotlin.R
import com.fullstack405.bitcfinalprojectkotlin.adapter.AttendAllAdapter
import com.fullstack405.bitcfinalprojectkotlin.data.UserAttendData
import com.fullstack405.bitcfinalprojectkotlin.databinding.FragmentAttendAllBinding
import com.fullstack405.bitcfinalprojectkotlin.databinding.FragmentAttendCompleteBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AttendCompleteFragment : Fragment() {
    private lateinit var binding: FragmentAttendCompleteBinding
    private lateinit var attendAllAdapter: AttendAllAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttendCompleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // activity에서 userId 추출
        val userId = activity?.intent!!.getLongExtra("userId", 0)

        // 신청내역 리스트에 보이는 데이터
        var compList = mutableListOf<UserAttendData>()
        // 신청하고 참석 Y 미참석 N
        compList.add(UserAttendData(0, 0, "제2회 국제 컨퍼런스", "20241011", 'Y'))

        attendAllAdapter = AttendAllAdapter(compList)
        binding.recyclerViewComp.adapter = attendAllAdapter
        binding.recyclerViewComp.layoutManager = LinearLayoutManager(requireContext())

        // userId 별 attend_info 리스트를 불러오고 승인된 이벤트 리스트 전부 불러와서
        // attendinfo 에 있는 행사 id랑 비교해가면서 제목 끌어와서 userattenddata에 세팅 하나 ??
        // 일단 아이넴뷰 형식에 맞게 데이터 세팅하고 어댑터 연결


        // db 연결버전
        // userid랑 Y,N 보내서 내역 걸러오기
//        Client.retrofit.findFinalAttendList(userId,'Y').enqueue(object:retrofit2.Callback<List<UserAttendData>>{
//            override fun onResponse(
//                call: Call<List<UserAttendData>>,response: Response<List<UserAttendData>>) {
//                compList = response.body() as MutableList<UserAttendData>
//                attendAllAdapter.notifyDataSetChanged()
//            }
//
//            override fun onFailure(call: Call<List<UserAttendData>>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })

    }
}