import Events from "../../pages/Events.jsx";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import PresidentView from "../common/PresidentView.jsx";
import SecretaryView from "../common/SecretaryView.jsx";



function EventView () {
  const { eventId } = useParams();
  const navigate = useNavigate();

  const [eventData, setEventData] = useState(null);
  const [error, setError] = useState(null);

  const [scheduleList, setScheduleList] = useState([]);
  const [userData, setUserData] = useState([]);
  const [approver, setApprover] = useState([]);

  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [eventAccept, setEventAccept] = useState('');


  const [startTime, setStartTime] = useState(null);
  const [endTime, setEndTime] = useState(null);
  const [uploader, setUploader] = useState('');
  const [prover, setProver] = useState('');
  // const [uploadDate, setUploadDate] = useState('');
  const uploadDate = new Date();

  const formatDate = (uploadDate) => {
    const year = uploadDate.getFullYear();
    const month = String(uploadDate.getMonth() + 1).padStart(2, '0'); // 0부터 시작하므로 +1
    const day = String(uploadDate.getDate()).padStart(2, '0');
    // const hours = String(uploadDate.getHours()).padStart(2, '0');
    // const minutes = String(uploadDate.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day}`;
    // return `${year}-${month}-${day} ${hours}:${minutes}`;
  };




  // 이벤트 포스터를 표시하는 함수
  const posterView = () => {
    if (eventData && eventData.eventPoster) {
      return (
        <div>
          <img src={`http://localhost:8080/eventImg/${eventData.eventPoster}`} alt="Poster" className={'mw-100'}/>
        </div>
      );
    }
    return null;
  };

  const nonData = () => {
    alert("행사정보가 존재하지 않는 경로입니다.");
    window.location.href = "/";
  }

  useEffect(() => {
    axios
      .get(`http://localhost:8080/event/${eventId}`)
      .then((response) => {
        if (response.data) {
          setEventData(response.data);
          setEventAccept(response.data);
          setScheduleList(response.data.eventSchedule || []);
          setUserData(response.data.posterUser || {});
          setApprover(response.data.approver || {});

        } else {
          setError("데이터를 찾을 수 없습니다.");
        }
      })
      .catch((err) => {
        setError("서버 오류가 발생했습니다.");
      });
  }, [eventId]);

  // 데이터가 로드된 이후 상태 업데이트
  useEffect(() => {

    if (scheduleList.length > 0) {
      setStartDate(scheduleList[0].eventDate);
      setEndDate(scheduleList[scheduleList.length - 1].eventDate);
      setStartTime(scheduleList[0].startTime);
      setEndTime(scheduleList[0].endTime);
    }
    if (userData) {
      setUploader(userData.name || '');
    }
  }, [eventData, scheduleList, userData, approver]);




  return (
    <section>
      <Events/>

      {eventData ? (
        <div className={'form-border'}>
          <div className={'py-4 border-bottom fs-5'}>
            {eventData.eventTitle || '제목 없음'}
          </div>
          <div className={'d-flex py-3 border-bottom justify-content-between'}>
            <div className={'w-50'}>행사기간 <span className={'ms-3 fw-bold'}>{startDate || '미정'} ~ {endDate || '미정'}</span></div>
            <div className={'w-50'}>행사시간 <span className={'ms-3 fw-bold'}>{startTime || '미정'} ~ {endTime || '미정'}</span></div>
          </div>

          <div className={'d-flex py-3 border-bottom justify-content-between'}>
            <div className={'w-50'}>모집시작일 <span className={'ms-3 fw-bold'}>{eventData.visibleDate || '미정'}</span></div>
            <div className={'w-50'}>정원수 <span className={'ms-3 fw-bold'}>{eventData.maxPeople != 0 && eventData.maxPeople + '명' || eventData.maxPeople === 0 && '제한 없음'}</span></div>
          </div>

          <div className={'d-flex py-3 border-bottom justify-content-between'}>
            <div className={'w-50'}>작성일 <span
              className={'ms-3 fw-bold'}>{formatDate(uploadDate).toLocaleString() || '미정'}</span></div>
            {/*<div className={'w-50'}>작성일 <span className={'ms-3 fw-bold'}>{eventData.uploadDate || ''}</span></div>*/}
            <div className={'w-50'}>작성자 <span className={'ms-3 me-2'}> {uploader}</span></div>
          </div>

          <div className={'d-flex py-3 border-bottom justify-content-between'}>
            <div className={'w-50'}>승인일자 <span className={'ms-3 fw-bold'}>
              {eventData.eventAccept === 1 && '미승인' ||
                eventData.eventAccept === 2 && eventData.acceptedDate ||
                eventData.eventAccept === 3 && '미승인'}
              {/*{eventData.acceptedDate || '미승인'}*/}
            </span></div>
            <div className={'w-50'}>승인자 <span className={'ms-3 fw-bold'}>
             {eventData.eventAccept === 1 && '미승인' ||
               eventData.eventAccept === 2 && approver?.name ||
               eventData.eventAccept === 3 && '미승인'}
              {/*{approver?.name || '미승인'}*/}
            </span></div>
          </div>

          <div className={'bg-light p-5 border-bottom'}>
            {posterView()} {/* 포스터 이미지 렌더링 */}
            {eventData.eventContent || '내용 없음'}
          </div>

          {/* 협회장 / 총무 다른 button view */}
          {
            sessionStorage.getItem('permission') === '협회장' && (
              <PresidentView />
            )}
          {
            sessionStorage.getItem('permission') === '총무' && (
              <SecretaryView eventId={eventId}/>
            )}
        </div>
      ) : (
        <div>...로딩중</div>
      )}
    </section>
  );
}

export default EventView;