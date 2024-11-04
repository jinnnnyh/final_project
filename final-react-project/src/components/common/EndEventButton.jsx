
import axios from "axios";
import {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";

function EndEventButton() {
  const [eventData, setEventData] = useState([]);
  const {eventId} = useParams();
  const [eventAccept, setEventAccept] = useState('');
  const [isRegistrationOpen, setIsRegistrationOpen] = useState();
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get(`http://13.209.112.29:8080/api/event/${eventId}`)
      .then((response) => {
        if (response.data) {
          setEventData(response.data);
          setEventAccept(response.data);
          setIsRegistrationOpen(response.data);
        } else {
          setError("데이터를 찾을 수 없습니다.");
        }
      })
      .catch((err) => {
        setError("서버 오류가 발생했습니다.");
      });
  }, [eventId]);


  // 모집종료 버튼 (Y : 모집중, N : 모집완료)
  // 승인대기일때 (1) 모집완료 Y => 승인대기중 alert
  // 승인완료일때 (2) 모집완료 Y => 모집종료여부 확인 confirm => 모집완료 N
  // 승인거부일때 (3) => 자동 모집완료 N
  const handleEndEvent = async () => {
    if (eventData.eventAccept === 1) {
      alert("승인대기 중인 행사입니다.");
    } else if (eventData.eventAccept === 2) {
      if (eventData.isRegistrationOpen === 'Y') {
        const confirmed = window.confirm("행사 모집 종료하시겠습니까?");
        if (confirmed) {
          alert("행사 모집종료되었습니다.");

          navigate(`/event/${eventId}`);
          window.location.reload()
          // window.location.href = `/event/${eventId}`
          const response = await axios.put(`http://13.209.112.29:8080/api/event/endEvent/${eventId}`)
          setEventData(eventData.filter(eventData => eventData.eventId !== eventId));
          setEventAccept(response.data);
          setIsRegistrationOpen(response.data);
        } else {
          // console.error("모집종료 중 오류 발생:", error);
        }
      } else if (eventData.isRegistrationOpen === 'N') {
        alert("이미 모집완료된 행사입니다.");
      } else {
        setError("서버 오류가 발생했습니다.");
      }
    }else if (eventData.eventAccept === 3) {
      alert("승인거부된 행사입니다.");
    } else {
      setError("서버 오류가 발생했습니다.");
    }
  }


  return (
  <button type={'button'} style={{border: 'none', background: 'none'}} onClick={() => handleEndEvent()}>
    {eventData.isRegistrationOpen === 'Y' && eventData.eventAccept === 1 &&
      <span className={'btn'} style={{color:'#aaa', border:'1px solid #ccc', backgroundColor:'#f4f4f4'}}>모집종료</span> ||

      eventData.isRegistrationOpen === 'Y' && eventData.eventAccept === 2 && <span className={'btn btn-outline-danger'}>모집종료</span> ||
      eventData.isRegistrationOpen === 'N' && eventData.eventAccept === 2 &&
      <span className={'btn'} style={{color:'#aaa', border:'1px solid #ccc', backgroundColor:'#f4f4f4'}}>모집종료</span> ||

      eventData.isRegistrationOpen === 'Y' && eventData.eventAccept === 3 &&
      <span className={'btn'} style={{color:'#aaa', border:'1px solid #ccc', backgroundColor:'#f4f4f4'}}>모집종료</span>
    }
  </button>
)
}

export default EndEventButton;