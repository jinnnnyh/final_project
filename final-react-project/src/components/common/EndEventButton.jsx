
import axios from "axios";
import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";

function EndEventButton() {
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

  const handleEndEvent = async () => {
    if (isButtonDisabled) {
      alert("이미 처리된 내용입니다.");
      return;
    }

    // 모집종료 버튼
    const confirmed = window.confirm('행사 모집 종료하시겠습니까?');

    if (confirmed) {
      try {
        const dataToSave = { id: eventId, key: "isRegistrationOpen" };
        await axios.put(`http://localhost:8080/event/endEvent/${eventId}`, dataToSave);
        setEventData(eventData.filter(eventData => eventData.eventId !== eventId));
        setIsButtonDisabled(true);
        localStorage.setItem('isButtonDisabled', 'true'); // 상태를 로컬 스토리지에 저장
        alert("행사 모집종료되었습니다.");
      } catch (error) {
        console.error("모집종료 중 오류 발생:", error);
        alert("모집종료 중 오류가 발생했습니다.");
      }
    }
  };
  

  return (

    <button type={'button'} className={'btn btn-danger'}  onClick={handleEndEvent} disabled={isButtonDisabled}>
      {isButtonDisabled ? "모집종료" : "모집종료"}
    </button>
  )
}

export default EndEventButton;