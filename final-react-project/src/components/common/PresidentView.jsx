import {useNavigate} from "react-router-dom";
import EndEventButton from "./EndEventButton.jsx";
import DenyEventButton from "./DenyEventButton.jsx";
import AcceptEventButton from "./AcceptEventButton.jsx";

function PresidentView() {
  const navigate = useNavigate();

  return (
   // 협회장 view
    <div className={'d-flex justify-content-between mt-5'}>
      <div className={'justify-content-start'}>
        <button type={'button'} className={'btn btn-outline-dark'} onClick={() => navigate('/')}>목록</button>
      </div>
      <div className={'justify-content-end'}>
        <DenyEventButton/>  {/* 승인거부 버튼 */}
        <AcceptEventButton/> {/* 승인여부 버튼 */}
        <EndEventButton/>  {/* 모집 종료 버튼 */}
      </div>
    </div>
  )
}

export default PresidentView;