import {Link} from "react-router-dom";
import Notice from "../../pages/Notice.jsx";

function NoticeBoardList () {

  return (
    <section>
      <Notice/>
      <table className={'table table-custom'}>
        <colgroup>
          <col width={"10%"}/>
          <col width={"auto"}/>
          <col width={"15%"}/>
          <col width={"15%"}/>
        </colgroup>
        <thead>
        <tr>
          <th scope={'col'}>번호</th>
          <th scope={'col'}>제목</th>
          <th scope={'col'}>작성자</th>
          <th scope={'col'}>게시일</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>1</td>
          <td>공지사항 등록 예정입니다.공지사항 등록 예정입니다.</td>
          <td>총무</td>
          <td>24.10.01</td>
        </tr>
        <tr>
          <td>2</td>
          <td>공지사항 등록 예정입니다.공지사항 등록 예정입니다.</td>
          <td>총무</td>
          <td>24.10.01</td>
        </tr>
        <tr>
          <td>3</td>
          <td>공지사항 등록 예정입니다.공지사항 등록 예정입니다.</td>
          <td>총무</td>
          <td>24.10.01</td>
        </tr>
        </tbody>

      </table>

      {/*<Pagination/>*/}

      <div className={'d-flex justify-content-end mt-5'}>
        <Link to={"/notice/write"} className={'btn btn-point'}> 글쓰기</Link>
      </div>
    </section>
  )
}

export default NoticeBoardList;