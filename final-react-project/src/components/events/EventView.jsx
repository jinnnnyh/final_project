import Events from "../../pages/Events.jsx";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import PresidentButton from "../common/PresidentButton.jsx";
import SecretaryButton from "../common/SecretaryButton.jsx";



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
  const [startTime, setStartTime] = useState(null);
  const [endTime, setEndTime] = useState(null);
  const [uploader, setUploader] = useState('');
  const [prover, setProver] = useState('');
  const [uploadDate, setUploadDate] = useState('');


  // 이벤트 포스터를 표시하는 함수
  const posterView = () => {
    if (eventData && eventData.eventPoster) {
      return (
        <div>
          <img src={`http://localhost:8080/eventImg/${eventData.eventPoster}`} alt="Poster" />
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
        console.log(response.data);
        if (response.data) {
          setEventData(response.data);
          setScheduleList(response.data.scheduleList || []);
          setUserData(response.data.posterUser || {});
          setApprover(response.data.approver || {});
        } else {
          setError("데이터를 찾을 수 없습니다.");
        }
      })
      .catch((err) => {
        setError("서버 오류가 발생했습니다.");
        console.error(err)
      });
  }, [eventId]);

  // 데이터가 로드된 이후 상태 업데이트
  useEffect(() => {
    if (eventData) {
      // console.log(eventData);
      // const eventDataSplit1 = eventData.upload.split('T');
      // console.log(eventData.uploadDate);
      // const year = eventData.uploadDate.getFullYear();
      // const month = eventData.uploadDate.getMonth();
      // const date = eventData.uploadDate.getDate();
      // const hour = eventData.uploadDate.getHours();
      // const mins = eventData.uploadDate.getMinutes();
      // setUploadDate(`${year}-${month}-${date} ${hour}:${mins}`);
    }
    if (scheduleList.length > 0) {
      setStartDate(scheduleList[0].eventDate);
      setEndDate(scheduleList[scheduleList.length - 1].eventDate);
      setStartTime(scheduleList[0].startTime);
      setEndTime(scheduleList[0].endTime);
    }
    if (userData) {
      setUploader(userData.name || '');
    }
    if (approver) {
      console.log(approver);
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
          <div className={'d-flex py-4 border-bottom justify-content-between'}>
            <div className={'w-50'}>행사기간 <span className={'ms-3 fw-bold'}>{startDate || '미정'} ~ {endDate || '미정'}</span></div>
            <div className={'w-50'}>행사시간 <span className={'ms-3 fw-bold'}>{startTime || '미정'} ~ {endTime || '미정'}</span></div>
          </div>

          <div className={'d-flex py-4 border-bottom justify-content-between'}>
            <div className={'w-50'}>모집시작일 <span className={'ms-3 fw-bold'}>{eventData.visibleDate || '미정'}</span></div>
            <div className={'w-50'}>인원수 <span className={'ms-3 fw-bold'}>{eventData.maxPeople || 0}명</span></div>
          </div>

          <div className={'d-flex py-4 border-bottom justify-content-between'}>
            <div className={'w-50'}>작성일 <span className={'ms-3 fw-bold'}>{eventData.uploadDate || '미정'}</span></div>
            <div className={'w-50'}>작성자 <span className={'ms-3 me-2'}>{uploader || '알 수 없음'}</span></div>
          </div>

          <div className={'d-flex py-4 border-bottom justify-content-between'}>
            <div className={'w-50'}>승인일자 <span className={'ms-3 fw-bold'}>{eventData.acceptedDate || '미정'}</span></div>
            <div className={'w-50'}>승인자 <span className={'ms-3 fw-bold'}>{approver?.name || '미정'}</span></div>
          </div>

          <div className={'bg-light p-5 border-bottom'}>
            {posterView()} {/* 포스터 이미지 렌더링 */}
            {eventData.eventContent || '내용 없음'}
          </div>

          {/* 협회장 / 총무 다른 button view */}
          {
            sessionStorage.getItem('permission') === '협회장' && (
            <PresidentButton />
          )}
          {
            sessionStorage.getItem('permission') === '총무' && (
            <SecretaryButton/>
          )}
        </div>
      ) : (
        <div>...로딩중</div>
      )}
    </section>
  );
}

export default EventView;