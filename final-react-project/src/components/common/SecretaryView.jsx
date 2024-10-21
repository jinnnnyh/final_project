import {useNavigate, useParams} from "react-router-dom";
import EndEventButton from "./EndEventButton.jsx";
import DeleteEventButton from "./DeleteEventButton.jsx";

function SecretaryView(props) {
  const navigate = useNavigate();
  const eventId = props.eventId;


  // 게시물 수정
  const handleUpdate = () => {
    navigate(`/event/updateEvent/${eventId}?mode=update`);
  }


  return (
 // 글 작성자 view
  <div className={'d-flex justify-content-between mt-5'}>
   <div className={'justify-content-start'}>
     <button type={'button'} className={'btn btn-outline-dark'} onClick={() => navigate('/')}>목록</button>
  </div>
   <div className={'justify-content-end'}>
     <DeleteEventButton/>  {/* 삭제 버튼 */}
     <button type={'button'} className={'btn btn-outline-point me-2'}  onClick={handleUpdate}>수정</button>
     <EndEventButton/>  {/* 모집종료 버튼 */}
   </div>
  </div>
  )
}

export default SecretaryView;