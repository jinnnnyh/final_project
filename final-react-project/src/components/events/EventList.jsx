import Events from "../../pages/Events.jsx";
import {NavLink, useLocation, Link} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";

function EventList () {
  const [eventData, setEventData] = useState([
    {eventId: 1, image:'/noimg.png', eventTitle: 'AI 행사1', eventDate:'2024년 10월 15일', startTime:'오후 2시', endTime:'오후 5시',
      visibleDate:'2024년 10월 1일', approval:'승인예정', deadline:'진행 중'},
    {eventId: 2, image:'/noimg.png', eventTitle: 'AI 행사1', eventDate:'2024년 10월 15일', startTime:'오후 2시', endTime:'오후 5시',
      visibleDate:'2024년 10월 1일', approval:'승인완료', deadline:'마감'},

  ])


  // 리스트 데이터 불러옴
  // const [eventData, setEventData] = useState();
  //
  // useEffect(() => {
  //   axios.get('http://localhost:8080/event')
  //     .then(res => {
  //       setEventData(res.data);
  //       //console.log(eventData);
  //     })
  //     .catch(err => {
  //       alert("통신 실패." + err);
  //     });
  // }, [eventData]);

  const moveToEventWrite = () => window.location.href = '/event/write';

  // 상세페이지에서 승인한 결과 DB 정보에 따라 색상 다르게 출력
  const approvalBtnStyle = (item) => {
    switch (item) {
      case '승인예정':
        return {
          color: '#dc3545',
          border: '1px solid #dc3545',
        };
      case '승인완료':
        return {
          color: '#283eae',
          border: '1px solid #283eae',
        };
      default:
        return {};
    }
  };


  // 행사 진행상황 버튼 DB 정보에 따라 색상 다르게 출력(진행중 / 마감)
  // 진행중 버튼은 클릭하면 마감으로 글자 변경되고 비활성화됨, 마감여부 DB에 전달
  // 마감 조건 : 관리자가 직접 마감, 신청자 정원초과 자동 마감, 행사일 지나면 자동 마감

  // const [status, setStatus] = useState('진행 중'); // 초기 상태
  // const [loading, setLoading] = useState(true);
  // useEffect(() => {
  //   const fetchData = async () => {
  //     try {
  //       const response = await axios.get('/api/status'); // 데이터 가져오기
  //       setStatus(response.data.status); // 상태 업데이트
  //     } catch (error) {
  //       console.error('Error fetching data:', error);
  //     } finally {
  //       setLoading(false);
  //     }
  //   };
  //
  //   fetchData();
  // }, []);

  const handleClick = () => {
    if (eventData === '진행 중') {
      setEventData('마감'); // 버튼 클릭 시 상태 변경
    }
  };

  // if (loading) return <p>Loading...</p>; // 로딩 상태 처리


  return (
    <section>
      <Events/>
        <div className={'d-flex justify-content-end mb-5'}>
          <button type={'button'} className={'btn btn-danger'} onClick={moveToEventWrite}>행사 등록</button>
        </div>

      {eventData?.map(item => (
        <div key={item.eventId} className={'d-flex justify-content-between align-items-center pb-5'}>
          <div className={'col-3'}><Link to={`/events/${item.eventId}`}> <img src={item.image} alt={item.eventTitle} className={'w-100'}/></Link></div>
          <div className={'col-9 ps-5 d-flex align-items-center'}>

            <div className={'col-10'}>
              <p className={'mb-2 approval-btn'} style={approvalBtnStyle(item.approval)}>{item.approval}</p> {/* 승인예정 / 승인완료 */}
              <Link to={`/events/${item.eventId}`}><h4>{item.eventTitle}</h4></Link>
              <ul className={'ps-0 mt-3'}>
                <li>행사기간 : <span>{item.eventDate}</span></li>
                <li className={'my-1'}>행사시간 : <span>{item.startTime} ~ {item.endTime}</span></li>
                <li>게시일 : <span>{item.visibleDate}</span></li>
              </ul>
            </div>
            <div className={'col-2'}>
              <NavLink to={'/events/attend'} className={'btn w-100 btn-point'}>참석현황</NavLink>
              <button type={'submit'} className={'btn w-100 mt-2'}
                onClick={handleClick}
                disabled={eventData === '마감'} // 상태에 따라 비활성화
                style={{
                  backgroundColor: eventData === '진행 중' ? 'gray' : 'gray',
                  color: 'white',
                  cursor: eventData === '마감' ? 'not-allowed' : 'pointer',
                }}
              >
                {item.deadline}
              </button>{/* 진행 중 / 마감 */}

            </div>
          </div>
        </div>
        ))}
    </section>
  )
}

export default EventList;