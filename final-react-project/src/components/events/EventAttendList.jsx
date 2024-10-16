import EventAttendDay from "./EventAttendDay.jsx";
import { useNavigate, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";

function EventAttendList() {
  const { eventId } = useParams();
  const navigate = useNavigate();

  const [eventData, setEventData] = useState({});
  const [dayWiseAttendData, setDayWiseAttendData] = useState({});
  const [eventSchedules, setEventSchedules] = useState([]);

  const [activeTab, setActiveTab] = useState('overall');

  const [totalAttendees, setTotalAttendees] = useState(0);
  const [completedAttendees, setCompletedAttendees] = useState(0);

  useEffect(() => {
    axios.get(`http://localhost:8080/event/attendList/${eventId}`)
      .then(res => {
        const eventInfo = res.data;
        setEventData(eventInfo);
        setEventSchedules(eventInfo.eventScheduleDTOList);
        const groupedByDay = {};
        let total = 0;
        let completed = 0;

        eventInfo.attendUserList.forEach(user => {
          total++;
          if (user.eventComp === 'Y') {
            completed++;
          }

          user.attendInfoDTOList.forEach((attendInfo, index) => {
            const day = index + 1;
            if (!groupedByDay[day]) {
              groupedByDay[day] = [];
            }
            groupedByDay[day].push({
              ...user,
              ...attendInfo
            });
          });
        });

        setDayWiseAttendData(groupedByDay);
        setTotalAttendees(total);
        setCompletedAttendees(completed);
      })
      .catch(err => {
        alert("데이터를 불러오는 중 오류 발생: " + err);
      });
  }, [eventId]);

  const handleList = () => {
    navigate('/');
  };

  const overallAttendData = eventData.attendUserList || [];

  return (
    <section>
      <h4 className="mb-5">참석자 현황 리스트</h4>
      <h4>{eventData.eventTitle}</h4>
      <h5 className="mb-3">정원: <strong>{eventData.maxPeople > 0 ? eventData.maxPeople : '인원수 제한 없음'}</strong></h5>
      <div className="d-flex py-3 justify-content-between">
        <div className="w-50">
          행사기간 : <span className="ms-3 fw-bold">{eventData.startDate} ~ {eventData.endDate}</span>
        </div>
        <div className="w-50">
          행사시간 : <span className="ms-3 fw-bold">{eventData.startTime} ~ {eventData.endTime}</span>
        </div>
      </div>

      <div className="tabs">
        <div
          className={`tab ${activeTab === 'overall' ? 'active' : ''}`}
          onClick={() => setActiveTab('overall')}
        >
          전체 참석자
        </div>
        <div
          className={`tab ${activeTab === 'daily' ? 'active' : ''}`}
          onClick={() => setActiveTab('daily')}
        >
          일차별 참석자
        </div>
      </div>

      {activeTab === 'overall' ? (
        <div>
          <h5>전체 참석자 목록</h5>
          <div className="mb-3">
            <span>신청자 수 : <strong>{totalAttendees}</strong></span> |
            <span className="ms-3">수료자 수 : <strong>{completedAttendees}</strong></span>
          </div>
          <table className="table table-custom mb-5 table-hover">
            <thead>
            <tr>
              <th scope="col">번호</th>
              <th scope="col">이름</th>
              <th scope="col">전화번호</th>
              <th scope="col">소속기관</th>
              <th scope="col">직위</th>
              <th scope="col">수료여부</th>
            </tr>
            </thead>
            <tbody>
            {overallAttendData.map((user, index) => (
              <tr key={index}>
                <td>{index + 1}</td>
                <td>{user.name}</td>
                <td>{user.userPhone}</td>
                <td>{user.userDepart}</td>
                <td>
                  {user.role === 'ROLE_SECRETARY' && '총무'}
                  {user.role === 'ROLE_PRESIDENT' && '협회장'}
                  {user.role === 'ROLE_REGULAR' && '정회원'}
                </td>
                <td>
                  {user.eventComp === 'Y' ? '수료' : '미수료'}
                </td>
              </tr>
            ))}
            </tbody>
          </table>
        </div>
      ) : (
        Object.keys(dayWiseAttendData).map((day, index) => {
          const eventDate = eventSchedules[index]?.eventDate || '';
          return (
            <EventAttendDay
              key={index}
              day={day}
              attendData={dayWiseAttendData[day]}
              eventStartTime={eventData.startTime}
              eventEndTime={eventData.endTime}
              eventDate={eventDate}
              eventSchedules={eventSchedules}
            />
          );
        })
      )}

      <div className="d-flex justify-content-end">
        <button type="button" className="btn btn-point" onClick={handleList}>목록보기</button>
      </div>
    </section>
  );
}

export default EventAttendList;