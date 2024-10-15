import Events from "../../pages/Events.jsx";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import Pagination from "../common/Pagination.jsx";

function EventAttendList () {

  // 리스트 데이터 불러옴
  const [eventData, setEventData] = useState([]);
  const [eventListData, setEventListData] = useState([]);
  const { eventId } = useParams();

  const [eventTitle, setEventTitle] = useState(null);
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [startTime, setStartTime] = useState(null);
  const [endTime, setEndTime] = useState(null);
  const [totalAppliedPeople, setTotalAppliedPeople] = useState(null);  // 신청
  const [maxPeople, setMaxPeople] = useState(null);
  const [completedPeople, setCompletedPeople] = useState(null); // 수료

  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(8); // 한 페이지당 항목 수
  const [selectedOption, setSelectedOption] = useState('전체');

  useEffect(() => {
    axios.get(`http://localhost:8080/event/attendList/${eventId}`)
      .then(res => {
        // setEventData(res.data);
        setEventListData(res.data);
        setEventData(res.data.item || ['']); // 데이터 없을 경우 빈 배열로 설정
        setEventListData(res.data.item || ['']);
        // console.log(Array.isArray(eventData));

        setEventTitle(res.data.eventTitle);
        setStartDate(res.data.startDate);
        setEndDate(res.data.endDate);
        setStartTime(res.data.startTime);
        setEndTime(res.data.endTime);
        setTotalAppliedPeople(res.data.totalAppliedPeople); // 신청
        setMaxPeople(res.data.maxPeople);
        setCompletedPeople(res.data.completedPeople); // 수료

      })
      .catch(err => {
        alert("통신 실패." + err);
      });
  }, [eventId]);


  // 목록보기 버튼
  const navigate = useNavigate();
  const handleList = () => {
    navigate('/'); // 목록 경로
  };



  useEffect(() => {
    // 선택된 옵션에 따라 데이터 필터링
    if (selectedOption === 'all') {
      setEventListData(eventData);
    } else {
      setEventListData(eventData.filter(item => item.name === selectedOption));
    }
  }, [selectedOption, eventData]);


  // 페이징 현재 페이지에 표시할 데이터
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const eventListItems = eventListData.slice(indexOfFirstItem, indexOfLastItem);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleSelectChange = (event) => {
    setSelectedOption(event.target.value);
    setCurrentPage(1);// 옵션 변경 시 페이지를 1로 리셋
  };








  return (
    <section>
      <Events/>
      <h4 className={'mb-5'}>참석자 현황 리스트</h4>
      <h4>{eventTitle || '미정'}</h4>
      <div className={'d-flex py-2 justify-content-between'}>
        <div className={'w-50'}>행사기간 :<span className={'ms-2 fw-bold'}>{startDate || '미정'} ~ {endDate || '미정'}</span></div>
        <div className={'w-50'}>행사시간 :<span className={'ms-2 fw-bold'}>{startTime || '미정'} ~ {endTime || '미정'}</span></div>
      </div>
      <div className={'d-flex pb-3 justify-content-between'}>
        <div className={'w-50'}>신청인원 / 정원 :<span className={'ms-2 fw-bold'}>{totalAppliedPeople || '0'}명 / {maxPeople || '0'}명</span></div>
        <div className={'w-50'}>참석인원 / 수료인원 :<span className={'ms-2 fw-bold'}>{totalAppliedPeople || '0'}명 / {completedPeople || '0'}명</span></div>
      </div>

      <div>

        {/* SELECT 구분, 전체, 1일차, 2일차.... */}
        {/* DB에 저장되는 일차에 따라 자동으로 SELECT 옵션값 조정 */}
        <div className={'d-flex justify-content-end'}>
          <p className={'text-danger'}>※ 행사 회차별 확인 가능</p>
          <select onChange={handleSelectChange} value={selectedOption} className={'ms-2 mb-3 px-2'}>
            <option value="all">전체</option>
            <option value="1">1일차</option>
            <option value="2">2일차</option>
          </select>
        </div>

        <table className={'table table-custom mb-5'}>
          <colgroup>
            <col width={"7%"}/>
            <col width={"10%"}/>
            <col width={"auto"}/>
            <col width={"10%"}/>
            <col width={"15%"}/>
            <col width={"10%"}/>
            <col width={"10%"}/>
            <col width={"10%"}/>
            <col width={"10%"}/>
          </colgroup>
          <thead>
          <tr>
            <th scope={'col'}>번호</th>
            <th scope={'col'}>아이디</th>
            <th scope={'col'}>이름</th>
            <th scope={'col'}>전화번호</th>
            <th scope={'col'}>소속기관</th>
            <th scope={'col'}>직위</th>
            <th scope={'col'}>참석여부</th>
            <th scope={'col'}>입장시간</th>
            <th scope={'col'}>퇴장시간</th>
          </tr>
          </thead>
          <tbody>

          {
            eventListItems.map(item => {
              return (
                <tr key={item.eventId}>
                  <td>{item.userId}</td>
                  <td>{item.userAccount}</td>
                  <td>{item.name}</td>
                  <td>{item.userPhone}</td>
                  <td>{item.userDepart}</td>
                  <td>{item.role}</td>
                  <td>{item.attendComp}</td>
                  <td>{item.checkInTime}</td>
                  <td>{item.checkOutTime}</td>
                </tr>
              );
            })
          }
          </tbody>
        </table>
        <Pagination
          currentPage={currentPage}
          itemsCount={eventListData.length}
          itemsPerPage={itemsPerPage}
          onPageChange={handlePageChange}
        />


        <div className={'d-flex justify-content-end'}>
          <button type={"button"} className={'btn btn-point'} onClick={handleList}>목록보기</button>
        </div>
      </div>

    </section>
  )
}

export default EventAttendList;