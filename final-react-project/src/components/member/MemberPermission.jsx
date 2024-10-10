import {useEffect, useState} from "react";
import axios from "axios";
import {NavLink} from "react-router-dom";

function MemberPermission () {

  // 앱에서 회원가입한 준회원 list 불러오기
  const [memberPermission, setMemberPermission] = useState([
    {id: 1, userAccount:'iu', name:'아이유', userPhone:'010-1111-1111', userDepart: 'AI협회', role: '일반회원', approve:'승인대기'},
    {id: 2, userAccount:'iu', name:'아이유', userPhone:'010-1111-2222', userDepart: 'AI협회', role: '일반회원', approve:'승인완료'},
    {id: 3, userAccount:'iu', name:'아이유', userPhone:'010-1111-3333', userDepart: 'AI협회', role: '일반회원', approve:'승인완료'},
  ]);

  // const [memberPermission, setMemberPermission] = useState();
  //
  // useEffect(() => {
  //   axios.get('http://localhost:8080/member')
  //     .then(res => {
  //       setMemberPermission(res.data);
  //       //console.log(memberPermission);
  //     })
  //     .catch(err => {
  //       alert("통신 실패." + err);
  //     });
  // }, [memberPermission]);


  // 승인대기 버튼 클릭 => 승인알림창 => 확인 => 승인완료
  // 승인완료한 버튼 클릭 = > 승인 취소 알림창 => 확인 => 승인대기로 변경
  // 승인대기 / 승인완료 버튼 색 다름
  
  // 승인완료 후 일주일이내 취소 가능함
  // 승인완료 후 1주일 이후 승인확정 => 승인대기페이지에서 자동삭제됨



  const [statuses, setStatuses] = useState('승인대기');
  const handleButtonClick = (id) => {
    const currentStatus = statuses[id];
    const newStatus = currentStatus === '승인대기' ? '승인완료' : '승인대기'; // 상태 전환
    const confirmationMessage = currentStatus === '승인대기'
      ? '승인하시겠습니까??'
      : '승인 취소하시겠습니까?';

    if (window.confirm(confirmationMessage)) {
      setStatuses((prevStatuses) => ({
        ...prevStatuses,
        [id]: newStatus,
      }));
      // sendDataToDB(id, newStatus); // 데이터베이스에 전송
    }
  };



  // 데이터베이스에 데이터 전송하는 함수
  // const sendDataToDB = async (id, newStatus) => {
  //   try {
  //     const response = await axios.post('http://localhost:8080/member', {
  //       id,
  //       status: newStatus,
  //     });
  //
  //     console.log('서버 응답:', response.data);
  //   } catch (error) {
  //     console.error('에러 발생:', error);
  //   }
  // };

  const buttonStyle = {
    승인대기: { backgroundColor: 'blue', color: 'white' },
    승인완료: { backgroundColor: 'green', color: 'white' },
  };


  return (
    <section>
      <p className={'mb-1 text-black-50'}>CheckManager</p>
      <h1 className={'mb-4'}>승인대기</h1>
      <table className={'table table-custom'}>
        <colgroup>
          <col width={"7%"}/>
          <col width={"15%"}/>
          <col width={"auto"}/>
          <col width={"15%"}/>
          <col width={"20%"}/>
          <col width={"15%"}/>
          <col width={"15%"}/>
        </colgroup>
        <thead>
        <tr>
          <th scope={'col'}>번호</th>
          <th scope={'col'}>아이디</th>
          <th scope={'col'}>이름</th>
          <th scope={'col'}>전화번호</th>
          <th scope={'col'}>소속기관</th>
          <th scope={'col'}>직위</th>
          <th scope={'col'}>승인</th>
        </tr>
        </thead>
        <tbody>

        {
          memberPermission.map(item => {
            return (
              <tr key={item.id}>
                <td>{item.id}</td>
                <td>{item.userAccount}</td>
                <td>{item.name}</td>
                <td>{item.userPhone}</td>
                <td>{item.userDepart}</td>
                <td>{item.role}</td>
                <td>

                {/*/!*<button type={'submit'} className={'btn btn-point'}>{item.approve2}</button>*!/ /!*승인완료*!/*/}
              </td>
          </tr>
          );
          })
        }
        </tbody>
      </table>
    </section>
  )
}

export default MemberPermission;