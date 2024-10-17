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

  // 현재 날짜 가져오기
  const today = new Date();

  // 현재 페이지에 표시할 데이터
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const eventDataItems = eventData.slice(indexOfFirstItem, indexOfLastItem);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  // error 발생 시 대체 이미지로 이미지 설정
  const onErrorImg = (e) => {
    e.target.src = replace;
  };

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


  return (
    <section>
      <Events/>
      <div className={'d-flex justify-content-end mb-5'}>
        <button type={'button'} className={'btn btn-danger'} onClick={moveToEventWrite}>행사 등록</button>
      </div>
      {
        eventDataItems.map(item => {
          const visibleDate = new Date(item.visibleDate);
          let recruitmentStatus = '';

          if (item.eventAccept === 3) {
            recruitmentStatus = '';
          } else if (item.eventAccept === 1) {
            recruitmentStatus = '모집대기';
          } else if (item.eventAccept === 2) {
            if (today < visibleDate) {
              recruitmentStatus = '모집대기';
            } else if (today >= visibleDate && item.isRegistrationOpen === 'Y') {
              recruitmentStatus = '모집중';
            } else if (today >= visibleDate && item.isRegistrationOpen === 'N') {
              recruitmentStatus = '모집종료';
            }
          }

          return (
            <div key={item.eventId} className={'d-flex justify-content-between align-items-center pb-5'}>
              <div className={'col-3 thumbnail'}>
                <Link to={`/event/${item.eventId}`}>
                  <img
                    src={`http://localhost:8080/eventImg/${item.eventPoster}`}
                    alt={item.eventTitle}
                    className={'w-100'}
                    onError={onErrorImg}
                  />
                </Link>
              </div>
              <div className={'col-9 ps-5 d-flex align-items-center'}>
                <div className={'col-10'}>
                  <div className={'d-flex'}>
                    <div className={'markStyle'}>
                      {item.eventAccept === 1 && <p className={'redMark'}>승인대기</p> ||
                        item.eventAccept === 2 && <p className={'blueMark'}>승인완료</p> ||
                        item.eventAccept === 3 && <p className={'redMark'}>승인거부</p> ||
                        item.eventAccept === 'null' && <p className={'grayMark'}>null</p>
                      }
                    </div>
                    {recruitmentStatus && (
                      <div className={'markStyle ms-2'}>
                        <p className={recruitmentStatus === '모집중' ? 'redMark' : 'grayMark'}>
                          {recruitmentStatus}
                        </p>
                      </div>
                    )}
                  </div>

                  <Link to={`/event/${item.eventId}`}>
                    <h4>{item.eventTitle}</h4>
                  </Link>
                  <ul className={'ps-0 mt-3'}>
                    <li>행사기간 : <span>{item.startDate} ~ {item.endDate}</span></li>
                    <li className={'my-1'}>행사시간 : <span>{item.startTime} ~ {item.endTime}</span></li>
                    <li className={'my-1'}>모집시작일 : <span>{item.visibleDate}</span> | 모집마감일 : <span>{item.invisibleDate}</span></li>
                    <li className={'my-1'}>신청인원 / 정원 : <span>{item.totalAppliedPeople}명 / {item.maxPeople}명</span></li>
                    <li>수료인원 / 참석인원 : <span>{item.completedPeople}명 / {item.totalAppliedPeople}명</span></li>
                  </ul>
                </div>
                <div className={'col-2'}>
                  <NavLink to={`/event/attendList/${item.eventId}`} className={'btn btn-point px-3'}>
                    참석자현황<br/>자세히 보기 +
                  </NavLink>
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
  );
}

export default EventList;