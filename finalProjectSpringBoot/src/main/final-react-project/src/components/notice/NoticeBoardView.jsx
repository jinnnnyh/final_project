import Notice from "../../pages/Notice.jsx";
import {Link} from "react-router-dom";

function NoticeBoardView () {
  return (
    <section>
      <Notice/>
      <h3>view 페이지</h3>

      <div className={'d-flex justify-content-between mt-5'}>
        <div className={'justify-content-start'}>
          <button type={'button'} className={'btn btn-outline-danger mr-2'}>삭제</button>
        </div>
        <div className={'justify-content-end'}>
          <button type={'reset'} className={'btn btn-outline-point me-2'}>취소</button>
          <Link to={"/notice/write"} className={'btn btn-point'}> 글쓰기</Link>
        </div>
      </div>
    </section>

  )
}

export default NoticeBoardView;
