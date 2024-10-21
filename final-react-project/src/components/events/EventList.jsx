import Events from "../../pages/Events.jsx";
import { NavLink, Link } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios";
import Pagination from "../common/Pagination.jsx";

// 대체 이미지 import
import replace from '/noimg.png';

function EventList() {
  const [eventData, setEventData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(3);
  const [approvalFilter, setApprovalFilter] = useState('');
  const [statusFilter, setStatusFilter] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [uploaderSearchTerm, setUploaderSearchTerm] = useState('');
  const [approverSearchTerm, setApproverSearchTerm] = useState('');

  const today = new Date();
  today.setHours(0, 0, 0, 0)

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;

  const eventDataItems = eventData
    .filter(item => {
      if (approvalFilter !== '' && item.eventAccept !== parseInt(approvalFilter)) return false;

      let recruitmentStatus = '';

      if (item.eventAccept === 3) {
        recruitmentStatus = '모집불가';
      }
      else if (item.eventAccept === 1) {
        recruitmentStatus = '모집대기';
      }
      else if (item.eventAccept === 2) {
        if (today >= new Date(item.visibleDate) && today <= new Date(item.invisibleDate)) {
          recruitmentStatus = '모집중';
        }
        else if (today < new Date(item.visibleDate)) {
          recruitmentStatus = '모집대기';
        }
        else if (today > new Date(item.invisibleDate) && today < new Date(item.startDate)) {
          recruitmentStatus = '행사대기';
        }
        else if (today >= new Date(item.startDate) && today <= new Date(item.endDate)) {
          recruitmentStatus = '행사중';
        }
        else {
          recruitmentStatus = '행사종료';
        }
      }

      if (statusFilter !== '' && recruitmentStatus !== statusFilter) return false;

      if (searchTerm && !item.eventTitle.includes(searchTerm)) return false;

      if (uploaderSearchTerm && !item.eventUploaderName.includes(uploaderSearchTerm)) return false;

      if (approverSearchTerm && item.eventApproverName && !item.eventApproverName.includes(approverSearchTerm)) return false;

      return true;
    })
    .slice(indexOfFirstItem, indexOfLastItem);

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
      <Events />
      <div className={'d-flex justify-content-end'}>
        <button type={'button'} className={'btn btn-danger'} onClick={moveToEventWrite}>행사 등록</button>
      </div>
      <div className={'d-inline-flex justify-content-end mb-3'}>
        <select
          className={'form-select me-2'}
          value={approvalFilter}
          onChange={(e) => setApprovalFilter(e.target.value)}
        >
          <option value=''>승인전체</option>
          <option value='1'>승인대기</option>
          <option value='2'>승인완료</option>
          <option value='3'>승인거부</option>
        </select>
        <select
          className={'form-select me-2'}
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value)}
        >
          <option value=''>상태전체</option>
          <option value='모집대기'>모집대기</option>
          <option value='모집중'>모집중</option>
          <option value='행사대기'>행사대기</option>
          <option value='행사중'>행사중</option>
          <option value='행사종료'>행사종료</option>
          <option value='모집불가'>모집불가</option>
        </select>
        <input
          type="text"
          className={'form-control me-2'}
          placeholder="행사 검색"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          style={{width: '250px'}}
        />
        <input
          type="text"
          className={'form-control me-2'}
          placeholder="등록자 검색"
          value={uploaderSearchTerm}
          onChange={(e) => setUploaderSearchTerm(e.target.value)}
          style={{width: '250px'}}
        />
        <input
          type="text"
          className={'form-control me-2'}
          placeholder="승인자 검색 (승인자 없음: 미승인)"
          value={approverSearchTerm}
          onChange={(e) => setApproverSearchTerm(e.target.value)}
          style={{width: '250px'}}
        />
      </div>
      {
        eventDataItems.map(item => {
          const visibleDate = new Date(item.visibleDate);
          visibleDate.setHours(0, 0, 0, 0)
          const invisibleDate = new Date(item.invisibleDate);
          invisibleDate.setHours(0,0,0,0)
          const startDate = new Date(item.startDate);
          startDate.setHours(0,0,0,0)
          const endDate = new Date(item.endDate);
          endDate.setHours(0,0,0,0)
          let recruitmentStatus = '';

          if (item.eventAccept === 3) {
            recruitmentStatus = '모집불가';
          }
          else if (item.eventAccept === 1) {
            recruitmentStatus = '모집대기';
          }
          else if (item.eventAccept === 2) {
            if (today >= visibleDate && today <= invisibleDate) {
              recruitmentStatus = '모집중';
            }
            else if (today< visibleDate) {
              recruitmentStatus = '모집대기';
            }
            else if (today > invisibleDate && today < startDate) {
              recruitmentStatus = '행사대기';
            }
            else if (today >= startDate && today <= endDate) {
              recruitmentStatus = '행사중';
            }
            else {
              recruitmentStatus = '행사종료';
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
                            <p className={recruitmentStatus === '행사중' && 'redMark' ||
                              recruitmentStatus === '행사대기' && 'blueMark' ||
                              recruitmentStatus === '모집대기' && 'blueMark' ||
                              recruitmentStatus === '모집중' && 'redMark' ||
                              recruitmentStatus === '행사종료' && 'grayMark' ||
                              recruitmentStatus === '모집불가' && 'grayMark'
                            }>
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
                        <li className={'my-1'}>신청인원 / 정원 : <span>{item.totalAppliedPeople}명 / {item.maxPeople === 0 && '제한없음' || item.maxPeople != 0 && `${item.maxPeople}명`}</span></li>
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