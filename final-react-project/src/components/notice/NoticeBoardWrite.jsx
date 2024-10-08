import Notice from "../../pages/Notice.jsx";

function NoticeBoardWrite () {
  return (
    <section>
      <Notice/>
      <div className={'form-border'}>
        <form>
          <div className={'mt-4'}>
            <label htmlFor={'noti-title'} className={'form-label'}>제목</label>
            <input type={'text'} className={'form-control py-2'} id={'noti-title'} placeholder={'제목을 입력해주세요'} />
          </div>

          <div className={'d-flex mt-4 justify-content-between'}>
            <div className={'w-50 me-4'}>
              <label htmlFor={'noti-writer'} className={'form-label'}>작성자</label>
              <input type={'text'} className={'form-control py-2'} placeholder={'작성자를 입력해주세요'} id={'noti-writer'}/>
            </div>
            <div className={'w-50'}>
              <label htmlFor={'noti-date'} className={'form-label'}>작성일</label>
              <input type={'datetime-local'} className={'form-control py-2'} id={'noti-date'}/>
            </div>
          </div>
          <div className={'mt-4'}>
            <label htmlFor={'noti-content'} className={'form-label'}>내용</label>
            <textarea  className={'form-control py-3'} id={'noti-content'} rows="5" placeholder={'내용을 입력해주세요'}/>
          </div>

          <div className={'d-flex justify-content-end mt-5'}>
            <button type={'reset'} className={'btn btn-outline-point me-3'}>취소</button>
            <button type={'submit'} className={'btn btn-point'}>완료</button>
          </div>
        </form>
      </div>
    </section>

  )
}

export default NoticeBoardWrite;
