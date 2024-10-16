import EventAttendDay from "./EventAttendDay.jsx";
import { useNavigate, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";

function EventAttendList() {
  const { eventId } = useParams();
  const navigate = useNavigate();

  const [eventData, setEventData] = useState({});
  const [dayWiseAttendData, setDayWiseAttendData] = useState({});

  useEffect(() => {
    axios.get(`http://localhost:8080/event/attendList/${eventId}`)
      .then(res => {
        const eventInfo = res.data;
        setEventData(eventInfo);

        // 참석 정보를 일차별로 분리
        const groupedByDay = {};

        eventInfo.attendUserList.forEach(user => {
          user.attendInfoDTOList.forEach((attendInfo, index) => {
            const day = index + 1; // attendId가 아닌 index 기반으로 일차 설정
            if (!groupedByDay[day]) {
              groupedByDay[day] = [];
            }
            groupedByDay[day].push({
              ...user, // 사용자 정보
              ...attendInfo // 해당 회차의 참석 정보
            });
          });
        });

        setDayWiseAttendData(groupedByDay);
      })
      .catch(err => {
        alert("데이터를 불러오는 중 오류 발생: " + err);
      });
  }, [eventId]);

  const handleList = () => {
    navigate('/'); // 목록 경로로 이동
  };

  return (
    <section>
      <h4 className="mb-5">참석자 현황 리스트</h4>
      <h4>{eventData.eventTitle}</h4>
      <div className="d-flex py-3 justify-content-between">
        <div className="w-50">
          행사기간 : <span className="ms-3 fw-bold">{eventData.startDate} ~ {eventData.endDate}</span>
        </div>
        <div className="w-50">
          행사시간 : <span className="ms-3 fw-bold">{eventData.startTime} ~ {eventData.endTime}</span>
        </div>
      </div>

      {/* 일차별 참석자 정보 출력 */}
      {Object.keys(dayWiseAttendData).map((day, index) => (
        <EventAttendDay key={index} day={day} attendData={dayWiseAttendData[day]} />
      ))}

      <div className="d-flex justify-content-end">
        <button type="button" className="btn btn-point" onClick={handleList}>목록보기</button>
      </div>
    </section>
  );
}

export default EventAttendList;


// import Member from "../../pages/Member.jsx";
// import Events from "../../pages/Events.jsx";
// import {NavLink, useNavigate} from "react-router-dom";
// import Login from "../../pages/Login.jsx";
// import {useEffect, useState} from "react";
//
// function EventAttendList () {
//
//   // const [eventAttendData, setEventAttendData] = useState([
//   //   {id: 1, num:1, userAccount:'iu', name:'아이유', userPhone:'010-1111-1111', userDepart: 'AI협회', role: '일반회원', attendComp:'참석',checkInTime:'오후 2시', checkOutTime:'오후 5시' },
//   //   {id: 2, num:2, userAccount:'js', name:'유재석', userPhone:'010-1111-1111', userDepart: 'AI협회', role: '일반회원', attendComp:'미참석',checkInTime:'오후 2시', checkOutTime:'오후 5시' },
//   //   {id: 3, num:3, userAccount:'iu', name:'아이유', userPhone:'010-1111-1111', userDepart: 'AI협회', role: '일반회원', attendComp:'참석',checkInTime:'오후 2시', checkOutTime:'오후 5시' },
//   // ]);
//   // 리스트 데이터 불러옴
//   // const [eventAttendData, setEventAttendData] = useState();
//   //
//   // useEffect(() => {
//   //   axios.get('http://localhost:8080/events')
//   //     .then(res => {
//   //       setEventAttendData(res.data);
//   //       //console.log(eventAttendData);
//   //     })
//   //     .catch(err => {
//   //       alert("통신 실패." + err);
//   //     });
//   // }, [eventData]);
//
//   // 목록보기 버튼
//   const navigate = useNavigate();
//   const handleList = () => {
//     navigate('/'); // 목록 경로
//   };
//
//
//   return (
//     <section>
//       <Events/>
//       <h4 className={'mb-5'}>참석자 현황 리스트</h4>
//       <h4>행사제목입니다.</h4>
//       <div className={'d-flex py-3 justify-content-between'}>
//         <div className={'w-50'}>행사기간 :<span className={'ms-3 fw-bold'}>2024.10.05.</span></div>
//         <div className={'w-50'}>행사시간 :<span className={'ms-3 fw-bold'}>오후 2시 ~ 오후 5시</span></div>
//       </div>
//
//       <div>
//         <table className={'table table-custom mb-5'}>
//           <colgroup>
//             <col width={"7%"}/>
//             <col width={"10%"}/>
//             <col width={"auto"}/>
//             <col width={"10%"}/>
//             <col width={"10%"}/>
//             <col width={"10%"}/>
//             <col width={"10%"}/>
//             <col width={"10%"}/>
//             <col width={"10%"}/>
//           </colgroup>
//           <thead>
//           <tr>
//             <th scope={'col'}>번호</th>
//             <th scope={'col'}>아이디</th>
//             <th scope={'col'}>이름</th>
//             <th scope={'col'}>전화번호</th>
//             <th scope={'col'}>소속기관</th>
//             <th scope={'col'}>직위</th>
//             <th scope={'col'}>참석여부</th>
//             <th scope={'col'}>입장시간</th>
//             <th scope={'col'}>퇴장시간</th>
//           </tr>
//           </thead>
//           <tbody>
//           {eventAttendData?.map(item => (
//           <tr key={item.id} >
//             <td>{item.num}</td>
//             <td>{item.userAccount}</td>
//             <td>{item.name}</td>
//             <td>{item.userPhone}</td>
//             <td>{item.userDepart}</td>
//             <td>{item.role}</td>
//             <td>{item.attendComp}</td>
//             <td>{item.checkInTime}</td>
//             <td>{item.checkOutTime}</td>
//           </tr>
//           ))}
//           </tbody>
//         </table>
//
//         <div className={'d-flex justify-content-end'}>
//           <button type={"button"} className={'btn btn-point'} onClick={handleList}>목록보기</button>
//         </div>
//       </div>
//
//     </section>
//   )
// }
//
// export default EventAttendList;