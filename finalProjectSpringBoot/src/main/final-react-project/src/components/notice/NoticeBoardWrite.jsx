import Notice from "../../pages/Notice.jsx";
import {Link} from "react-router-dom";

function NoticeBoardWrite () {
  return (
    <section>
      <Notice/>
      <h3>글쓰기 페이지</h3>
      <div className={'d-flex justify-content-end mt-5'}>
        <Link to={"/notice/view"} className={'btn btn-point'}>view</Link>
      </div>
    </section>

  )
}

export default NoticeBoardWrite;
