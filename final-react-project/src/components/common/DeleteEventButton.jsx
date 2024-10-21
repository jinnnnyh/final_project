import {useParams} from "react-router-dom";
import axios from "axios";
import {useState} from "react";


function DeleteEventButton() {
  const [eventData, setEventData] = useState([]);
  const { eventId } = useParams();


  // 게시물 삭제
  const handleDeleteEvent = async() => {
    const confirmed = window.confirm('게시물을 삭제하시겠습니까?');

    if (confirmed) {
      await axios.delete(`http://localhost:8080/event/deleteEvent/${eventId}`);
      setEventData(eventData.filter(eventData => eventData.eventId !== eventId));
      alert("게시물이 삭제되었습니다.");
      window.location.href = "/"; // 게시물 삭제 후 목록으로 이동
    } else {
      // console.error("삭제 중 오류 발생:", error);
    }
  };

  return (
    <button type={'button'} className={'btn btn-outline-danger me-2'} onClick={() => handleDeleteEvent(eventData.eventId)} >삭제</button>
  )
}

export default DeleteEventButton;