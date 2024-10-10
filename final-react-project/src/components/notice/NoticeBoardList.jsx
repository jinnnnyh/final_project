import {Link, useNavigate} from "react-router-dom";
import Notice from "../../pages/Notice.jsx";
import {useEffect, useState} from "react";
import axios from "axios";

function NoticeBoardList () {

  // 공지사항 리스트 불러오기
  const [notiBoardList, setNotiBoardList] = useState([]);
  //
  // useEffect(() => {
  //   axios.get('http://localhost:8080/notifications')
  //     .then(res => {
  //       setNotiBoardList(res.data);
  //       // console.log(notiData);
  //     })
  //     .catch(err => {
  //       alert("통신 실패." + err);
  //     });
  // }, [notiBoardList]);
  //

  // 글쓰기 버튼 연결
  const navigate = useNavigate();
  const handleWrite= () => {
    navigate('/notice/write');
  };
  
  
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
          <th scope={'col'}>작성일</th>
        </tr>
        </thead>
        <tbody>
        {
          notiBoardList.map(item => {
            return (
              <tr key={item.notiId}>
                <td>{item.notiId}</td>
                <td>{item.notiTitle}</td>
                <td>{item.user}</td>
                <td>{item.notiDate}</td>
              </tr>
            );
          })
        }
        </tbody>
      </table>

      {/*<Pagination/>*/}

      <div className={'d-flex justify-content-end mt-5'}>
        <button className={'btn btn-point'} onClick={handleWrite}> 글쓰기</button>
      </div>
    </section>
  )
}

export default NoticeBoardList;