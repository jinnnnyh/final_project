import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";

function DenyEventButton() {
  const [eventData, setEventData] = useState([]);
  const { eventId } = useParams();
  const [eventAccept, setEventAccept] = useState('');
  const [error, setError] = useState(null);


useEffect(() => {
    axios
      .get(`http://localhost:8080/event/${eventId}`)
      .then((response) => {
        if (response.data) {
          setEventData(response.data);
          setEventAccept(response.data);
        } else {
          setError("데이터를 찾을 수 없습니다.");
        }
      })
      .catch((err) => {
        setError("서버 오류가 발생했습니다.");
      });
  }, [eventId]);


  const handleDenyEvent = async () => {
      if(eventData.eventAccept  === 1 || eventData.eventAccept  === 2) {
        const confirmed = window.confirm("행사 승인 거부하시겠습니까?");
        if (confirmed) {
          alert("승인 거부되었습니다.");
          window.location.href = `/event/${eventId}`
          const response = await axios.put(`http://localhost:8080/event/denyEvent/${eventId}`)
          setEventData(eventData.filter(eventData => eventData.eventId !== eventId));
          setEventAccept(response.data);
        } else {
          // console.error("승인 중 오류 발생:", error);
        }
      }
      else if (eventData.eventAccept  === 3) {
        alert("승인 거부된 행사입니다.");
      }
      else  {
        alert("승인 거부된 행사입니다.");
      }
    }


  return (
    <button type={'button'} style={{border:'none', background:'none'}} onClick={() => handleDenyEvent()}>
      {eventData.eventAccept === 1 && <span className={'btn btn-outline-danger'} >승인거부</span> ||
        eventData.eventAccept === 2 && <span className={'btn btn-outline-danger'} >승인거부</span> ||
        eventData.eventAccept === 3 && <span className={'btn'} style={{color:'#aaa', border:'1px solid #ccc', backgroundColor:'#f4f4f4'}}>승인거부</span>
      }
    </button>

  )
}

export default DenyEventButton;