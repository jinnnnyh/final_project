import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";



function AcceptEventButton() {
    const {eventId} = useParams();
    const [eventData, setEventData] = useState([]);
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



    // 행사 승인 버튼 (승인대기 / 승인완료)
    const handleAcceptEvent = async() => {
        if(eventData.eventAccept  === 1 || eventData.eventAccept  === 3) {
            const confirmed = window.confirm('행사 승인하시겠습니까?');
            if (confirmed) {
                const userId = sessionStorage.getItem("userId");
                const response = await axios.put(`http://localhost:8080/event/acceptEvent/${eventId}`, { newValue: '승인완료' }, {
                    params: { userId: userId }
                })
                    .then(() => {
                        alert("승인되었습니다.");
                        window.location.href = `/event/${eventId}`
                    })
                setEventData(eventData.filter(eventData => eventData.eventId !== eventId));
                setEventAccept(response.data);
            } else {
                // console.error("승인 중 오류 발생:", error);
            }
        } else if (eventData.eventAccept  === 2) {
            const confirmCancel = window.confirm('승인 취소하시겠습니까?');

            if (confirmCancel) {
                const userId = sessionStorage.getItem("userId");
                const response = await axios.put(`http://localhost:8080/event/acceptCancel/${eventId}`, { newValue: '승인대기' }, {
                    params: { userId: userId }
                })
                    .then(() => {
                        alert("승인취소되었습니다.");
                        window.location.href = `/event/${eventId}`
                    })
                setEventData(eventData.filter(eventData => eventData.eventId !== eventId));
                setEventAccept(response.data);
            } else {
                // console.error("승인 중 오류 발생:", error);
            }
        }else  {
            alert("승인거부된 행사입니다.");
            // console.error("승인 중 오류 발생:", error);
        }
    }


    return (
        <button type={'button'} style={{border:'none', background:'none'}}  onClick={() => handleAcceptEvent()}>
            {eventData.eventAccept === 1 && <span className={'btn btn-outline-point'} >승인하기</span> ||
                eventData.eventAccept === 2 && <span className={'btn btn-point'} >승인취소</span> ||
                eventData.eventAccept === 3 && <span className={'btn btn-outline-point'} >승인대기</span>
            }
        </button>
    )
}
export default AcceptEventButton;