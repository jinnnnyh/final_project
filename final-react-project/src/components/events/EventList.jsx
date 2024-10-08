import Events from "../../pages/Events.jsx";
import {NavLink} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";

function EventList () {
  const eventData = [
    {id: 1, image:'/noimg.png', eventTitle: 'AI 행사1', eventDate:'2024년 10월 15일', startTime:'오후 2시', endTime:'오후 5시', visibleDate:'2024년 10월 1일'},
    {id: 2, image:'/noimg.png', eventTitle: 'AI 행사1', eventDate:'2024년 10월 15일', startTime:'오후 2시', endTime:'오후 5시', visibleDate:'2024년 10월 1일'},

  ];


  // const [eventData, setEventData] = useState();
  //
  // useEffect(() => {
  //   axios.get('http://localhost:8080/events')
  //     .then(res => {
  //       setEventData(res.data);
  //       //console.log(eventData);
  //     })
  //     .catch(err => {
  //       alert("통신 실패." + err);
  //     });
  // }, [eventData]);


  return (
    <section>
      <Events/>
        <div className={'d-flex justify-content-end mb-5'}>
          <button type={'submit'} className={'btn btn-point'}>행사 등록</button>
        </div>

      {eventData?.map(item => (
        <div key={item.id}  className={'d-flex justify-content-between align-items-center pb-5'}>
          <div className={'col-3'}><img src={item.image} alt={item.eventTitle} className={'w-100'}/></div>
          <div className={'col-9 ps-5 d-flex'}>
            <div className={'col-10'}>
              <h4>{item.eventTitle}</h4>
              <ul className={'ps-0 mt-3'}>
                <li>행사기간 : <span>{item.eventDate}</span></li>
                <li className={'my-1'}>행사시간 : <span>{item.startTime} ~ {item.endTime}</span></li>
                <li>게시일 : <span>{item.visibleDate}</span></li>
              </ul>
            </div>
            <div className={'col-2'}>
              <NavLink to={'/events/attend'} className={'btn w-100 btn-point'}>참석현황</NavLink>
              <button type={'button'} className={'btn w-100 btn-outline-danger my-2'}>승인예정</button>
              <button type={'submit'} className={'btn w-100 btn-danger'}>마감</button>
            </div>
          </div>
        </div>
      ))}
    </section>
  )
}

export default EventList;