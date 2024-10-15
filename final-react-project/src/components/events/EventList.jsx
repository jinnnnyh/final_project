import Events from "../../pages/Events.jsx";
import {NavLink, Link} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import Pagination from "../common/Pagination.jsx";

// 대체 이미지 import
import replace from '/noimg.png';


function EventList () {

  // 리스트 데이터 불러옴
  const [eventData, setEventData] = useState([]);
  const [loading, setLoading] = useState(true);

  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(3); // 한 페이지당 항목 수


  // 현재 페이지에 표시할 데이터
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const eventDataItems = eventData.slice(indexOfFirstItem, indexOfLastItem);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };


// error 발생 시 대체 이미지로 이미지 설정
  const onErrorImg = (e) => {
    e.target.src = replace
  }


  useEffect(() => {
    axios.get('http://localhost:8080/event/list')

      .then(res => {
        console.log(res.data);
        if (res.data) {
          setEventData(res.data);
          setLoading(false);
        } else {
          alert("데이터를 찾을 수 없습니다.");
          setLoading(false);
        }
      })
      .catch(err => {
        alert("서버 오류가 발생했습니다." + err);
      });
  }, []);

  const moveToEventWrite = () => window.location.href = '/event/write';

  if (loading) {
    return <div>Loading...</div>;
  }



  // 모집여부 표시
  let message;
  let className;

  if (eventData.isRegistrationOpen === 'Y') {
    message = '모집중';
    className = 'redMark';
  } else if (eventData.isRegistrationOpen === "N") {
    message = '마감';
  }  else {
    message = 'Null';
  }

  // 승인여부 표시
  if (eventData.eventAccept === "1") {
    message = '승인대기';
    className = 'redMark';
  } else if (eventData.eventAccept === "2") {
    message = '승인완료';
    className = 'blueMark';
  }else if (eventData.eventAccept === "3") {
    message = '승인거부';
  } else {
    message = 'Null';
  }


  return (
    <section>
      <Events/>
      <div className={'d-flex justify-content-end mb-5'}>
        <button type={'button'} className={'btn btn-danger'} onClick={moveToEventWrite}>행사 등록</button>
      </div>
      {
        eventDataItems.map(item => {
          return (
        <div key={item.eventId} className={'d-flex justify-content-between align-items-center pb-5'}>
          <div className={'col-3 thumbnail'}><Link to={`/event/${item.eventId}`}> <img
            src={`http://localhost:8080/eventImg/${item.eventPoster}`} alt={item.eventTitle}
            className={'w-100'} onError={onErrorImg}/></Link></div>
          <div className={'col-9 ps-5 d-flex align-items-center'}>

            <div className={'col-10'}>
              <div className={'d-flex'}>
                <p className={'mb-2 markStyle className'}>
                  {message}
                </p> {/* 승인예정 / 승인완료 */}
                <p className={'mb-2 markStyle className ms-2'}>
                  {message}
              </p>  {/* 모집중 / 마감 */}
              </div>

              <Link to={`/event/${item.eventId}`}><h4>{item.eventTitle}</h4></Link>
              <ul className={'ps-0 mt-3'}>
                <li>행사기간 : <span>{item.startDate} ~ {item.endDate}</span></li>
                <li className={'my-1'}>행사시간 : <span>{item.startTime} ~ {item.endTime}</span></li>
                <li className={'my-1'}>게시일 : <span>{item.uploadDate}</span></li>
                <li className={'my-1'}>신청인원 / 정원 : <span>{item.totalAppliedPeople}명 / {item.maxPeople}명</span></li>
                <li>수료인원 / 참석인원 : <span>{item.completedPeople}명 / {item.totalAppliedPeople}명</span></li>
              </ul>
            </div>
            <div className={'col-2'}>
              <NavLink to={`/event/attendList/${item.eventId}`} className={'btn btn-point px-3'}>참석자현황<br/>자세히 보기 +</NavLink>
            </div>

          </div>
        </div>
          );
        })
      }

      <Pagination
        currentPage={currentPage}
        itemsCount={eventData.length}
        itemsPerPage={itemsPerPage}
        onPageChange={handlePageChange}
      />
    </section>
  )
}

export default EventList;