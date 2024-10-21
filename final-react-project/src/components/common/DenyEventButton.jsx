import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";

function DenyEventButton() {
  const [eventData, setEventData] = useState([]);
  const { eventId } = useParams();

  const [isButtonDisabled, setIsButtonDisabled] = useState(() => {
    // 로컬 스토리지에서 해당 ID의 값을 읽기
    const savedValue = localStorage.getItem(`buttonDisabled-${eventId}`);
    return savedValue === 'true';
  });

  useEffect(() => {
    // 상태가 변경될 때 로컬 스토리지에 저장
    localStorage.setItem(`buttonDisabled-${eventId}`, isButtonDisabled);
  }, [isButtonDisabled, eventId]);

  const handleDenyEvent = async () => {
    if (isButtonDisabled) {
      alert("이미 처리된 내용입니다.");
      return;
    }

    // 행사승인 버튼
    const confirmed = window.confirm("행사 승인하시겠습니까?");
    if (confirmed) {
      try {
        const dataToSave = { id: eventId, key: "eventAccept" };
        await axios.put(`http://localhost:8080/event/denyEvent/${eventId}`, dataToSave);
        setEventData(eventData.filter(eventData => eventData.eventId !== eventId));
        alert("승인 거부되었습니다.");
        setIsButtonDisabled(true);
      } catch (error) {
        console.error("승인 중 오류 발생:", error);
        alert("승인 중 오류가 발생했습니다.");
      }
    }
  };

  return (
  <button type={'button'} className={'btn btn-outline-danger me-2'} onClick={handleDenyEvent} disabled={isButtonDisabled}>
    {isButtonDisabled ? "승인거부" : "승인거부"}
  </button>

)
}

export default DenyEventButton;