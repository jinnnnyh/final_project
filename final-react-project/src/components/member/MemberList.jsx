import Member from "../../pages/Member.jsx";
import {useEffect, useState} from "react";
import axios from "axios";
import {NavLink, useNavigate} from "react-router-dom";


function MemberList () {

  // 리스트 데이터 불러오기
  const [memberList, setMemberList] = useState([
    {eventId: 1, userAccount:'iu', name:'아이유', userPhone:'010-1111-1111', userDepart: 'AI협회', role: '일반회원'},
    {eventId: 2, userAccount:'iu', name:'아이유', userPhone:'010-1111-2222', userDepart: 'AI협회', role: '일반회원'},
    {eventId: 3, userAccount:'iu', name:'아이유', userPhone:'010-1111-3333', userDepart: 'AI협회', role: '일반회원'},
  ]);

  // const [memberList, setMemberList] = useState();
  //
  // useEffect(() => {
  //   axios.get('http://localhost:8080/member')
  //     .then(res => {
  //       setMemberList(res.data);
  //       //console.log(memberList);
  //     })
  //     .catch(err => {
  //       alert("통신 실패." + err);
  //     });
  // }, [memberList]);

  // 게시물 체크
  const listCheck = (eventId) => {
    const checkedCount = memberList.filter(item => item.checked).length;

    // 이미 체크된 항목이 있을 때
    if (checkedCount > 0 && !memberList.find(item => item.eventId === eventId).checked) {
      alert('하나의 항목만 체크할 수 있습니다.');
      return;
    }

    setMemberList(memberList.map(item =>
      item.eventId === eventId ? { ...item, checked: !item.checked } : { ...item, checked: false } // 체크된 항목 외에는 모두 해제
    ));

  };

  // 리스트 체크 후 삭제버튼 클릭 시 삭제
  const btnCheckedDelete = () => {
    const confirmed = window.confirm('체크한 게시물을 삭제하시겠습니까?');
    if (confirmed) {
      setMemberList(memberList.filter(item => !item.checked));
    }
  };

  // 리스트 체크 후 수정버튼 클릭 시 수정페이지로 연결
  const navigate = useNavigate();
  const btnCheckedEdit = () => {
    const selectedItem = memberList.find(item => item.checked);
    if (selectedItem) {
      // navigate(`/member/${selectedItem.eventId}`); // 수정 페이지로 이동
      navigate('/member/edit'); // 수정 페이지로 이동
    } else {
      alert('수정할 항목을 선택해주세요.');
    }
  };


  return (
    <section>
      <Member/>
      <div>
        <table className={'table table-custom'}>
          <colgroup>
            <col width={"7%"}/>
            <col width={"7%"}/>
            <col width={"auto"}/>
            <col width={"15%"}/>
            <col width={"20%"}/>
            <col width={"15%"}/>
            <col width={"15%"}/>
          </colgroup>
          <thead>
          <tr>
            <th scope={'col'}></th>
            <th scope={'col'}>번호</th>
            <th scope={'col'}>아이디</th>
            <th scope={'col'}>이름</th>
            <th scope={'col'}>전화번호</th>
            <th scope={'col'}>소속기관</th>
            <th scope={'col'}>직위</th>
          </tr>
          </thead>
          <tbody>
          {
            memberList.map(item => {
              return (
                <tr key={item.eventId}>
                  <td>
                    <div className="form-check">
                      <input className="form-check-input" type="checkbox" id="checkbox" checked={item.checked} onChange={() => listCheck(item.eventId)}/>
                      <label className="form-check-label" htmlFor="checkbox"></label>
                    </div>
                  </td>
                  <td>{item.eventId}</td>
                  <td>{item.userAccount}</td>
                  <td>{item.name}</td>
                  <td>{item.userPhone}</td>
                  <td>{item.userDepart}</td>
                  <td>{item.role}</td>
                </tr>
              );
            })
          }
          </tbody>
        </table>

        <div className={'d-flex justify-content-between mt-5'}>
          <div className={'justify-content-start'}>
          <button type={'button'} className={'btn btn-outline-danger'} onClick={btnCheckedDelete}>삭제</button>
          </div>
          <div className={'justify-content-end'}>
            <button type={'submit'} className={'btn btn-point'} onClick={btnCheckedEdit}>수정</button>
          </div>
        </div>
      </div>

    </section>
  )
}

export default MemberList;