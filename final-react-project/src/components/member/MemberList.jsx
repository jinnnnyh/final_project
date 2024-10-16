import Member from "../../pages/Member.jsx";
import {useEffect, useState} from "react";
import axios from "axios";
import Pagination from "../common/Pagination.jsx";
import {useParams} from "react-router-dom";


function MemberList () {

  const [memberList, setMemberList] = useState([]);
  const [memberListData, setMemberListData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(8); // 한 페이지당 항목 수
  const [selectedOption, setSelectedOption] = useState('all'); // 기본값 'all'
  const { userId } = useParams();

  useEffect(() => {
    axios.get('http://localhost:8080/user/userManage')
      .then(res => {
        setMemberList(res.data);
        setMemberListData(res.data);
        //console.log(memberList);
      })
      .catch(err => {
        alert("통신 실패." + err);
      });
  }, []);


  useEffect(() => {
    // 선택된 옵션에 따라 데이터 필터링
    if (selectedOption === 'all') {
      setMemberListData(memberList);
    } else {
      setMemberListData(memberList.filter(item => item.role === selectedOption));
    }
  }, [selectedOption, memberList]);


  // 페이징 현재 페이지에 표시할 데이터
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const memberItems = memberListData.slice(indexOfFirstItem, indexOfLastItem);

  // 페이지 번호 계산
  const getPostNumber = (index) => index + 1 + (currentPage - 1) * postsPerPage;


  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleSelectChange = (event) => {
    setSelectedOption(event.target.value);
    setCurrentPage(1);// 옵션 변경 시 페이지를 1로 리셋
  };


  // 회원탈퇴
  const handleDelete = async (userId) => {
    const confirmed = window.confirm('회원을 탈퇴처리 하시겠습니까?');
    try {
      await axios.delete(`http://localhost:8080/user/signOut/${userId}`);
      setMemberListData(memberListData.filter(item => item.userId !== userId));
      alert("회원이 삭제되었습니다.");
    } catch (error) {
      alert("삭제 중 오류가 발생했습니다.");
      // console.error("삭제 중 오류 발생:", error);
    }
  };

  // 승인여부
  const handleApproval = async(userId) => {
    if (sessionStorage.getItem('permission') !== '협회장') {
      return  alert("승인권한이 없습니다.")
    }

    const confirmed = window.confirm('승인 하시겠습니까?');
    try {
      await axios.post(`http://localhost:8080/user/signAccept/${userId}`);
      setMemberListData(memberListData.filter(item => item.userId !== userId));
      alert("승인되었습니다.");
    } catch (error) {
      alert("승인 중 오류가 발생했습니다.");
      // console.error("승인 중 오류 발생:", error);
    }
  };




// 역할 변환
  const getRoleName = (role) => {
    switch (role) {
      case 'ROLE_PRESIDENT' :
        return '협회장';
      case 'ROLE_SECRETARY' :
        return '총무';
      case 'ROLE_REGULAR' :
        return '정회원';
      case 'ROLE_ASSOCIATE' :
        return '준회원';
      case 'ROLE_DELETE' :
        return '탈퇴회원';
      default :
        return '알수없음';
    }
  };




  return (
    <section>
      <Member/>
      <div className={'d-flex justify-content-end mb-3'}>
        <p className={'text-danger mt-1 me-3'}>※ 직위별 구분 가능</p>
        <form action="">
          <select value={selectedOption} onChange={handleSelectChange} className={'px-2 py-1'}>
            <option value="all">전체</option>
            <option value="ROLE_PRESIDENT">협회장</option>
            <option value="ROLE_SECRETARY">총무</option>
            <option value="ROLE_REGULAR">정회원</option>
            <option value="ROLE_ASSOCIATE">준회원</option>
            <option value="ROLE_DELETE">탈퇴회원</option>
          </select>
        </form>
      </div>

      <div>
        <table className={'table table-custom'}>
          <colgroup>
            <col width={"8%"}/>
            <col width={"13%"}/>
            <col width={"13%"}/>
            <col width={"12%"}/>
            <col width={"auto"}/>
            <col width={"15%"}/>
            <col width={"10%"}/>
            <col width={"10%"}/>
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
            <th scope={'col'}>탈퇴</th>
          </tr>
          </thead>
          <tbody>
          {/* table - 직위 : 협회장, 총무, 준회원, 정회원, 탈퇴회원별로 정렬 (준회원은 제일 첫페이지에 보일 수 있게) */}
          {memberItems.map((item, index) => (
            <tr key={item.userId}>
              <td>{`${index + 1}`}</td>
              {/*<td>{item.userId}</td>*/}
              <td>{item.userAccount}</td>
              <td>{item.name}</td>
              <td>{item.userPhone}</td>
              <td>{item.userDepart}</td>
              <td>{getRoleName(item.role)}</td>
              <td>

                {/* 승인대기 버튼은 준회원만 표출 */}
                {item.role === 'ROLE_ASSOCIATE' ?
                  <button type={'button'} className={'btn btn-outline-point py-1'} onClick={() => handleApproval(item.userId)}>승인</button>
                  : <p></p>
                }
              </td>
              <td>
                {/* 탈퇴회원은 탈퇴버튼 없음 */}
                {item.role === 'ROLE_DELETE' ? <p></p>
                  : <button type={'button'} className={'btn btn-outline-danger py-1'} onClick={() => handleDelete(item.userId)}>회원탈퇴</button>
                }
              </td>
            </tr>
          ))}
          </tbody>
        </table>
        <Pagination
          currentPage={currentPage}
          itemsCount={memberListData.length}
          itemsPerPage={itemsPerPage}
          onPageChange={handlePageChange}
        />

      </div>
    </section>
  )
}

export default MemberList;