import Events from "../../pages/Events.jsx";

function EventView () {
  return (
    <section>
      <Events/>
      <div className={'form-border'}>
        <div className={'py-4 border-bottom fs-5'}>
          행사 제목입니다.
        </div>

        <div className={'d-flex py-4 border-bottom justify-content-between'}>
          <div className={'w-50'}>행사기간 <span className={'ms-3 fw-bold'}>2024.10.14.</span></div>
          <div className={'w-50'}>행사시간 <span className={'ms-3 fw-bold'}>오후 2시 ~ 오후 5시</span></div>
        </div>

        <div className={'d-flex py-4 border-bottom justify-content-between'}>
          <div className={'w-50'}>게시일 <span className={'ms-3 fw-bold'}>2024.10.01.</span></div>
          <div className={'w-50'}>인원수 <span className={'ms-3 fw-bold'}>20명</span></div>
        </div>

        <div className={'d-flex py-4 border-bottom justify-content-between'}>
          <div className={'w-50'}>작성일 <span className={'ms-3 fw-bold'}>2024.09.28.</span></div>
          <div className={'w-50'}>작성자 <span className={'ms-3 fw-bold'}>총무 아이유</span></div>
        </div>

        <div className={'d-flex py-4 border-bottom justify-content-between'}>
          <div className={'w-50'}>승인일자 <span className={'ms-3 fw-bold'}>2024.09.29.</span></div>
          <div className={'w-50'}>승인자 <span className={'ms-3 fw-bold'}>협회장 김회장</span></div>
        </div>
        <div className={'bg-light p-5 border-bottom'}>
          글자 + 포스터 <br/>
          write 에서 글작성 가능하고 첨부파일로 업로드한 포스터 표출 <br/>
          행사내용 행사내용 행사내용 행사내용 행사내용 행사내용 행사내용 행사내용 행사내용 행사내용
          행사내용 행사내용 행사내용 행사내용 행사내용 행사내용 행사내용 행사내용
        </div>

        <div className={'d-flex justify-content-between mt-5'}>
          <div className={'justify-content-start'}>
            <button type={'submit'} className={'btn btn-outline-danger'}>삭제</button>
          </div>
          <div className={'justify-content-end'}>
            <button type={'button'} className={'btn btn-outline-point me-2'}>취소</button>
            <button type={'button'} className={'btn btn-point'}>수정</button>
          </div>
        </div>
      </div>

    </section>
  )
}

export default EventView;
