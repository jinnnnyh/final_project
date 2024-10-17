import {useNavigate} from "react-router-dom";

function PresidentButton() {
  const navigate = useNavigate();

  return (
   // 협회장 view
    <div className={'d-flex justify-content-between mt-5'}>
      <div className={'justify-content-start'}>
        <button type={'button'} className={'btn btn-point'} onClick={() => navigate('/')}>목록</button>
      </div>
      <div className={'justify-content-end'}>
        <button type={'submit'} className={'btn btn-outline-danger me-2'}>거부</button>
        <button type={'button'} className={'btn btn-outline-point me-2'}>승인대기</button>
        <button type={'button'} className={'btn btn-outline-point'}>마감</button>
      </div>
    </div>
  )
}

export default PresidentButton;