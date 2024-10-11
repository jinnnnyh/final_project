import Events from "../../pages/Events.jsx";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";

function EventView () {
  const { eventId } = useParams();
  const navigate = useNavigate();

  const [eventData, setEventData] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const [scheduleList, setScheduleList] = useState([]);
  const [userData, setUserData] = useState([]);
  const [approver, setApprover] = useState([]);

  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [startTime, setStartTime] = useState(null);
  const [endTime, setEndTime] = useState(null);
  const [uploader, setUploader] = useState('');
  const [prover, setProver] = useState('');
  const [uploadDate, setUploadDate] = useState('')

  const posterView = () => {
      return (
          <div>
            <img src={`http://localhost:8080/eventImg/${eventData.eventPoster}`}/>
          </div>
      )
  }

  useEffect(() => {
    axios
      .get(`http://localhost:8080/event/${eventId}`)
      .then((response) => {
        if (response.data) {
          setEventData(response.data);
          setScheduleList(response.data.scheduleList || []);
          setUserData(response.data.posterUser || []);
          setApprover(response.data.approver || []);
        }
        else {
          setError("데이터를 찾을 수 없습니다.");
        }
      });
  }, [eventId]);

  useEffect(() => {
    if(eventData) {
      // console.log(eventData.uploadDate)
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
      setUploader(userData.name);
    }
    if (approver) {
      // setApprover(approver.name)
      console.log(approver)
    }
  }, [eventData, scheduleList, userData, approver]);

  return (
    <section>
      {/*<Events/>*/}

      {/*/!*{eventData?.map(item => (*!/*/}

      <div className={'form-border'}>
        <div className={'py-4 border-bottom fs-5'}>
          {eventData.eventTitle}
        </div>
        <div className={'d-flex py-4 border-bottom justify-content-between'}>
          <div className={'w-50'}>행사기간 <span className={'ms-3 fw-bold'}>{startDate} ~ {endDate}</span></div>
          <div className={'w-50'}>행사시간 <span className={'ms-3 fw-bold'}>{startTime} ~ {endTime}</span></div>
        </div>

        <div className={'d-flex py-4 border-bottom justify-content-between'}>
          <div className={'w-50'}>모집시작일 <span className={'ms-3 fw-bold'}>{eventData.visibleDate}</span></div>
          <div className={'w-50'}>인원수 <span className={'ms-3 fw-bold'}>{eventData.maxPeople}명</span></div>
        </div>

        <div className={'d-flex py-4 border-bottom justify-content-between'}>
          <div className={'w-50'}>작성일 <span className={'ms-3 fw-bold'}>{eventData.uploadDate}</span></div>
          <div className={'w-50'}>작성자 <span className={'ms-3 me-2'}>{userData.name}</span>
          </div>
        </div>

        <div className={'d-flex py-4 border-bottom justify-content-between'}>
          <div className={'w-50'}>승인일자 <span className={'ms-3 fw-bold'}>{eventData.acceptedDate}</span></div>
          <div className={'w-50'}>승인자 <span className={'ms-3 fw-bold'}>{approver.name}</span></div>
        </div>
        <div className={'bg-light p-5 border-bottom'}>
          {posterView()}
          {eventData.eventContent}
        </div>

      {/*  /!* 글 등록자 view *!/*/}

        <div className={'d-flex justify-content-between mt-5'}>
          <div className={'justify-content-start'}>
            <button type={'submit'} className={'btn btn-outline-danger'}>삭제</button>
          </div>
          <div className={'justify-content-end'}>
            <button type={'button'} className={'btn btn-outline-point me-2'}>수정</button>
            <button type={'button'} className={'btn btn-point'}>목록</button>
          </div>
        </div>

        {/* 협회장 view */}
        {/*<div className={'d-flex justify-content-between mt-5'}>*/}
        {/*  <div className={'justify-content-start'}>*/}
        {/*    <button type={'submit'} className={'btn btn-outline-danger'}>거부</button>*/}
        {/*  </div>*/}
        {/*  <div className={'justify-content-end'}>*/}
        {/*    <button type={'button'} className={'btn btn-outline-point me-2'}>승인대기</button>*/}
        {/*  </div>*/}
        {/*</div>*/}
      </div>
    {/*))}*/}

</section>
  )
}

export default EventView;
