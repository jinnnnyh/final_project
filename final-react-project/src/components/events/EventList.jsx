import Events from "../../pages/Events.jsx";
import {NavLink, useLocation, Link} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";


function EventList () {
  // 리스트 데이터 불러옴
  const [eventData, setEventData] = useState([]);
  const [isRegistrationOpen, setIsRegistrationOpen] = useState('');
  const [message, setMessage] = useState('');


  useEffect(() => {
    axios.get('http://localhost:8080/event/list')

      .then(response => {
        console.log(response.data);
        if (response.data) {
          setEventData(response.data);
          setIsRegistrationOpen(response.data);
          setMessage(response.data );
        } else {
          alert("데이터를 찾을 수 없습니다.");
        }
        setEventData(response.data);
      })
      .catch(err => {
        alert("서버 오류가 발생했습니다." + err);
      });
  }, []);

  const moveToEventWrite = () => window.location.href = '/event/write';

  // 승인여부
  const registrationStyle = () => {
    if (isRegistrationOpen === 'Y') {
      return {text: message || '승인완료', style: 'approval_y' };
    } else if (isRegistrationOpen === 'N') {
      return {text: message || '승인대기', style: 'approval_n' };
    }  else {
      return {text: 'null', style: 'approval_n' };
    }
  };
  const {text, style } = registrationStyle();


  // 모집중 / 마감
  // const deadlineStyle = (item) => {
  //   switch (item) {
  //     case '모집중':
  //       return {
  //         color: '#fff',
  //         backgroundColor: '#dc3545',
  //       };
  //     case '마감':
  //       return {
  //         color: '#fff',
  //         backgroundColor: '#666',
  //       };
  //     default:
  //       return {};
  //   }
  // };

  return (

    <section>
      <Events/>
      <div className={'d-flex justify-content-end mb-5'}>
        <button type={'button'} className={'btn btn-danger'} onClick={moveToEventWrite}>행사 등록</button>
      </div>
      {
        eventData.map(item => {
          return (
        <div key={item.eventId} className={'d-flex justify-content-between align-items-center pb-5'}>
          <div className={'col-3 thumbnail'}><Link to={`/event/${item.eventId}`}> <img
            src={`http://localhost:8080/eventImg/${item.eventPoster}`} alt={item.eventTitle}
            className={'w-100'}/></Link></div>
          <div className={'col-9 ps-5 d-flex align-items-center'}>

            <div className={'col-10'}>
              <div className={'d-flex'}>
                <p className={'mb-2 markStyle style'}>{text}</p> {/* 승인예정 / 승인완료 */}
                {/*<p className={'mb-2 markStyle ms-2'} style={deadlineStyle(item.deadline)}>{item.deadline}</p> /!* 모집중 / 마감 *!/*/}
              </div>
              <Link to={`/event/${item.eventId}`}><h4>{item.eventTitle}</h4></Link>
              <ul className={'ps-0 mt-3'}>
                <li>행사기간 : <span>{item.startDate} ~ {item.endDate}</span></li>
                <li className={'my-1'}>행사시간 : <span>{item.startTime} ~ {item.endTime}</span></li>
                <li className={'my-1'}>게시일 : <span>{item.uploadDate}</span></li>
                <li className={'my-1'}>신청자 / 정원 : <span>{item.appliedPeople}명 / {item.maxPeople}명</span></li>
                <li>수료인원 : <span>{item.completedPeople}명</span></li>
              </ul>
            </div>
            <div className={'col-2'}>
              <NavLink to={'/events/attend'} className={'btn btn-point w-100'}>참석현황</NavLink>
            </div>

          </div>
        </div>
          );
        })
      }
    </section>
  )
}

export default EventList;