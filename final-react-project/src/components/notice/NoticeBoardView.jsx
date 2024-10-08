import Notice from "../../pages/Notice.jsx";
import {Link} from "react-router-dom";

function NoticeBoardView () {
  return (
    <section>
      <Notice/>
      <div className={'form-border'}>
          <div className={'py-4 border-bottom fs-5'}>
            공지사항 등록 예정입니다.공지사항 등록 예정입니다.
          </div>

          <div className={'d-flex py-4 border-bottom justify-content-between'}>
              <div className={'w-50'}>작성자 <span className={'ms-3 fw-bold'}>아이유</span> </div>
              <div className={'w-50'}>작성일 <span className={'ms-3 fw-bold'}>2024-10-05 오전 11:20</span></div>
          </div>
        <div className={'bg-light p-5 border-bottom'}>
            공지사항 등록 예정입니다.공지사항 등록 예정입니다. 공지사항 등록 예정입니다.공지사항 등록 예정입니다.
            공지사항 등록 예정입니다.공지사항 등록 예정입니다. 공지사항 등록 예정입니다.공지사항 등록 예정입니다.
          공지사항 등록 예정입니다.공지사항 등록 예정입니다. 공지사항 등록 예정입니다.공지사항 등록 예정입니다.
          공지사항 등록 예정입니다.공지사항 등록 예정입니다. 공지사항 등록 예정입니다.공지사항 등록 예정입니다.
          공지사항 등록 예정입니다.공지사항 등록 예정입니다. 공지사항 등록 예정입니다.공지사항 등록 예정입니다.
          </div>

          <div className={'d-flex justify-content-between mt-5'}>
            <div className={'justify-content-start'}>
              <button type={'submit'} className={'btn btn-outline-danger'}>삭제</button>
            </div>
            <div className={'justify-content-end'}>
              <Link to={"/notice"} className={'btn btn-outline-point me-2'}> 취소</Link>
              <button type={'button'} className={'btn btn-point'}>수정</button>
            </div>
          </div>
      </div>
    </section>


  )
}

export default NoticeBoardView;
