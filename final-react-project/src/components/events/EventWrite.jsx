import Member from "../../pages/Member.jsx";
import Events from "../../pages/Events.jsx";

function EventWrite () {

  return (
    <section>
      <Events/>
      <div className={'col-10'}>
        <form className={'form-border'}>
          <div className="mt-4">
            <label htmlFor={'eventTitle'} className={'form-label'}>행사제목</label>
            <input type={'text'} className={'form-control py-3'} placeholder={'제목을 입력해주세요'} id={'eventTitle'}/>
          </div>

          <div className="mt-4">
            <label htmlFor={'eventDate'} className={'form-label'}>행사기간</label>
            <input type={'date'} className={'form-control py-3'} placeholder={'행사일정을 입력해주세요'} id={'eventDate'}/>
          </div>

          <div className={'mt-4'}>
            <label htmlFor={'eventContents'} className={'form-label'}>내용</label>
            <textarea className={'form-control py-3'} rows="5" placeholder={'내용을 입력해주세요'} id={'eventContents'}/>
          </div>

          <div className="input-group mt-4">
            <input type={'file'} className={'form-control py-2'} id={'file'}/>
            <label  htmlFor={'file'} className={'input-group-text'}>Upload</label>
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