import Events from "../../pages/Events.jsx";

function EventWrite () {
  let eventTitle;


  return (
    <section>
      <Events/>
      <div className={'form-border'}>
        <form>
          <div className="mt-4 py-2">
            <label htmlFor={'event-title'} className={'form-label'}>행사제목</label>
            <input type={'text'} className={'form-control'} placeholder={'제목을 입력해주세요'} id={'event-title'} value={eventTitle}/>
          </div>

          <div className={'d-flex py-2 justify-content-between'}>
            <div className="w-50 d-flex me-5">
              <div className={'w-50 me-3'}>
                <label htmlFor={'event-startdate'} className={'form-label'}>행사 시작기간</label>
                <input type={'time'} className={'form-control me-3'} placeholder={'행사 시작기간'} id={'event-startdate'}/>
              </div>
              <div className={'w-50'}>
                <label htmlFor={'end-enddate'} className={'form-label'}>행사 종료기간</label>
                <input type={'time'} className={'form-control'} placeholder={'행사 종료기간'} id={'event-enddate'}/>
              </div>
            </div>
            <div className="w-50 d-flex">
              <div className={'w-50 me-3'}>
                <label htmlFor={'start-time'} className={'form-label'}>행사 시작시간</label>
                <input type={'time'} className={'form-control me-3'} placeholder={'행사 시작시간'} id={'start-time'}/>
              </div>
              <div className={'w-50'}>
                <label htmlFor={'end-time'} className={'form-label'}>행사 종료시간</label>
                <input type={'time'} className={'form-control'} placeholder={'행사 종료시간'} id={'end-time'}/>
              </div>
            </div>
          </div>

          <div className={'d-flex py-2 justify-content-between'}>
            <div className="w-50 me-5">
              <label htmlFor={'visible-date'} className={'form-label'}>게시일</label>
              <input type={'datetime-local'} className={'form-control'} placeholder={'게시일'} id={'visible-date'}/>
            </div>
            <div className="w-50">
              <label htmlFor={'max-people'} className={'form-label'}>인원수</label>
              <input type={'text'} className={'form-control'} placeholder={'정원을 입력해주세요'} id={'max-people'}/>
            </div>
          </div>

          <div className={'d-flex py-2 justify-content-between'}>
            <div className="w-50 me-5">
              <label htmlFor={'upload-date'} className={'form-label'}>작성일</label>
              <input type={'datetime-local'} className={'form-control'} placeholder={'작성일'} id={'upload-date'}/>
            </div>
            <div className="w-50">
              <label htmlFor={'event-writer'} className={'form-label'}>작성자</label>
              <input type={'text'} className={'form-control'} placeholder={'작성자를 입력해주세요'} id={'event-writer'}/>
            </div>
          </div>

          <div className={'d-flex py-2 justify-content-between'}>
            <div className="w-50 me-5">
              <label htmlFor={'upload-date'} className={'form-label'}>승인일자</label>
              <input type={'datetime-local'} className={'form-control'} placeholder={'승인일자'} id={'upload-date'}/>
            </div>
            <div className="w-50">
              <label htmlFor={'event-approver'} className={'form-label'}>승인자</label>
              <input type={'text'} className={'form-control'} placeholder={'승인자를 입력해주세요'} id={'event-approver'}/>
            </div>
          </div>

          <div className={'mt-3'}>
            <label htmlFor={'event-content'} className={'form-label'}>내용</label>
            <textarea className={'form-control py-3'} rows="5" placeholder={'내용을 입력해주세요'} id={'event-content'}/>
          </div>

          {/* 파일등록 */}
          <div className="input-group mt-4">
            <input type={'file'} className={'form-control py-2'} id={'file'}/>
            <label htmlFor={'file'} className={'input-group-text'}>Upload</label>
          </div>

          {/* 버튼 */}
          <div className={'d-flex justify-content-end mt-5'}>
            <button type={'reset'} className={'btn btn-outline-point me-2'}>취소</button>
            <button type={'submit'} className={'btn btn-point'}>완료</button>
          </div>
        </form>
      </div>
    </section>
  )
}

export default EventWrite;