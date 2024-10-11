import Events from "../../pages/Events.jsx";
import {useNavigate, useParams} from "react-router-dom";
import {useState} from "react";

function EventView () {
  const { eventId } = useParams();

  const [eventData, setEventData] = useState([ {
    eventId, eventTitle: '행사제목입력', eventDate:'2024.10.14.', startTime:'오후 2시', endTime:'오후 5시', visibleDate:'2024.10.01.',
    maxPeople:'20', uploadDate:'2024.09.28.', eventPoster:'아이유', acceptedDate:'2024.09.29.', approverName:'김협회장',
    eventContent:'글자 + 포스터 write 에서 글작성 가능하고 첨부파일로 업로드한 포스터 표출'
  }
    ]);


  // 협회장, 글 작성자 버튼 다르게 view 출력
  // 협회장 view (버튼 : 거부, 승인여부)
  // 승인여부 상태 변경 버튼 (승인대기 / 승인완료)
  // 승인대기 => 알림창 => 확인 => 승인완료
  // 승인완료 => 알림창(취소) => 확인 => 승인대기
  // 거부 => 알림창 => 확인 => 목록으로 이동


  // 글 작성자 view (버튼 : 삭제, 수정, 목록)
  // 게시글 목록 버튼
  const navigate = useNavigate();
  const handleList = () => {
    navigate('/'); // 목록 경로
  };

  //  게시글 삭제, 수정 버튼 기능
  const handleDelete = () => {
    const confirmDelete = window.confirm("정말로 삭제하시겠습니까?");
    if (confirmDelete) {
      // 게시물 삭제
      setEventData(eventData.filter(item => item.eventId !== Number(eventId)));
      navigate('/');
    }
  };


  return (
    <section>
      <Events/>

      {eventData?.map(item => (

      <div className={'form-border'}>
        <div className={'py-4 border-bottom fs-5'}>
          {item.eventTitle}
        </div>
        <div className={'d-flex py-4 border-bottom justify-content-between'}>
          <div className={'w-50'}>행사기간 <span className={'ms-3 fw-bold'}>{item.eventDate}</span></div>
          <div className={'w-50'}>행사시간 <span className={'ms-3 fw-bold'}>{item.startTime} ~ {item.endTime}</span></div>
        </div>

        <div className={'d-flex py-4 border-bottom justify-content-between'}>
          <div className={'w-50'}>게시일 <span className={'ms-3 fw-bold'}>{item.visibleDate}</span></div>
          <div className={'w-50'}>인원수 <span className={'ms-3 fw-bold'}>{item.maxPeople}명</span></div>
        </div>

        <div className={'d-flex py-4 border-bottom justify-content-between'}>
          <div className={'w-50'}>작성일 <span className={'ms-3 fw-bold'}>{item.uploadDate}</span></div>
          <div className={'w-50'}>작성자 <span className={'ms-3 me-2'}>{item.eventPoster}</span>
          </div>
        </div>

        <div className={'d-flex py-4 border-bottom justify-content-between'}>
          <div className={'w-50'}>승인일자 <span className={'ms-3 fw-bold'}>{item.acceptedDate}</span></div>
          <div className={'w-50'}>승인자 <span className={'ms-3 fw-bold'}>{item.approverName}</span></div>
        </div>
        <div className={'bg-light p-5 border-bottom'}>
          {item.eventContent}
        </div>

        {/* 글 등록자 view */}
        <div className={'d-flex justify-content-between mt-5'}>
          <div className={'justify-content-start'}>
            <button type={'submit'} className={'btn btn-outline-danger'} onClick={handleDelete}>삭제</button>
          </div>
          <div className={'justify-content-end'}>
            <button type={'button'} className={'btn btn-outline-point me-2'}>수정</button>
            <button type={'button'} className={'btn btn-point'} onClick={handleList}>목록</button>
          </div>
        </div>

        {/* 협회장 view */}
        {/*<div className={'d-flex justify-content-between mt-5'}>*/}
        {/*  <div className={'justify-content-start'}>*/}
        {/*    <button type={'submit'} className={'btn btn-outline-danger'}>거부</button>*/}
        {/*  </div>*/}
        {/*  <div className={'justify-content-end'}>*/}
        {/*    <button type={'button'} className={'btn btn-outline-point me-2'}>승인대기</button>*/}
        {/*  </div>*/}
        {/*</div>*/}
      </div>
    ))}

</section>
  )
}

export default EventView;
