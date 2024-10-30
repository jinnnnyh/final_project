import Member from "../../pages/Member.jsx";
import {useEffect, useState} from "react";
import axios from "axios";
import Pagination from "../common/Pagination.jsx";
import {useParams} from "react-router-dom";


function MemberList () {

  const [memberListData, setMemberListData] = useState([]);
  const { userId } = useParams();

  const [filteredData, setFilteredData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10);
  const [searchIdForm, setSearchIdForm] = useState('');
  const [searchNameForm, setSearchNameForm] = useState('');
  const [selectedOption, setSelectedOption] = useState('');



  // 데이터 가져오기
  useEffect(() => {
    const userListData = async () => {
      const response = await axios.get('http://43.200.254.110:8080/user/userManage');
      setMemberListData(response.data);
      setFilteredData(response.data);
    };
    userListData();
  }, []);


  useEffect(() => {
    const result = memberListData.filter(user =>
      user.userAccount.toLowerCase().includes(searchIdForm.toLowerCase()) &&
      user.name.toLowerCase().includes(searchNameForm.toLowerCase()) &&

      (selectedOption ? user.role === selectedOption : true ) ||
      (selectedOption === 'president' && user.role === 'ROLE_PRESIDENT') ||
      (selectedOption === 'secretary' && user.role === 'ROLE_SECRETARY') ||
      (selectedOption === 'regular' && user.role === 'ROLE_REGULAR') ||
      (selectedOption === 'associate' && user.role === 'ROLE_ASSOCIATE') ||
      (selectedOption === 'deleted' && user.role === 'ROLE_DELETE')
    );
    setFilteredData(result);
  }, [searchIdForm, searchNameForm, selectedOption, memberListData]);


  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const memberListItems = filteredData.slice(indexOfFirstItem, indexOfLastItem);



  // 회원탈퇴
  const handleDelete = async(userId) => {
    const confirmed = window.confirm('회원을 탈퇴처리 하시겠습니까?');

    if (confirmed) {
      await axios.delete(`http://43.200.254.110:8080/user/signOut/${userId}`);
      setMemberListData(memberListData.filter(item => item.userId !== userId));
      alert("회원이 삭제되었습니다.");
    } else {
      // console.error("삭제 중 오류 발생:", error);
    }
  };

  // 승인여부
  const handleApproval = async(userId) => {
    // if (sessionStorage.getItem('permission') !== '협회장') {
    //   return  alert("승인권한이 없습니다.")
    // }

    const confirmed = window.confirm("승인 하시겠습니까?");

    if (confirmed) {
      await axios.put(`http://43.200.254.110:8080/user/signAccept/${userId}`);
      setMemberListData(memberListData.filter(item => item.userId !== userId));
      alert("승인되었습니다.");
    } else {
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
      <div className="d-flex mb-3">
        <input
          type="text"
          placeholder="아이디 검색"
          value={searchIdForm}
          onChange={(e) => setSearchIdForm(e.target.value)}
          className="form-control me-2"
        />
        <input
          type="text"
          placeholder="이름 검색"
          value={searchNameForm}
          onChange={(e) => setSearchNameForm(e.target.value)}
          className="form-control me-2"
        />
          <select
            className="form-select form-select-sm"
            value={selectedOption}
            onChange={(e) => setSelectedOption(e.target.value)}
          >
            <option value="">직위 : 전체</option>
            <option value="president">협회장</option>
            <option value="secretary">총무</option>
            <option value="regular">정회원</option>
            <option value="associate">준회원</option>
            <option value="deleted">탈퇴회원</option>
          </select>
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
            {memberListItems.map((item, index) => (
              <tr key={item.userId}>
                {/* userId 순서대로 정렬 */}
                <td> {((currentPage - 1) * itemsPerPage) + index + 1} </td>
                {/*<td>{item.userId}</td>*/}
                <td>{item.userAccount}</td>
                <td>{item.name}</td>
                <td>{item.userPhone}</td>
                <td>{item.userDepart}</td>
                <td>{getRoleName(item.role)}</td>
                <td>

                  {/* 승인대기 버튼은 준회원만 표출 */}
                  {item.role === 'ROLE_ASSOCIATE' ?
                    <button type={'button'} className={'btn btn-outline-point py-1'}
                            onClick={() => handleApproval(item.userId)}>승인</button>
                    : <p></p>
                  }
                </td>
                <td>
                  {/* 탈퇴회원은 탈퇴버튼 없음 */}
                  {item.role === 'ROLE_DELETE' ? <p></p>
                    : <button type={'button'} className={'btn btn-outline-danger py-1'}
                              onClick={() => handleDelete(item.userId)}>회원탈퇴</button>
                  }
                </td>
              </tr>
            ))}
            </tbody>
          </table>
          <Pagination
            currentPage={currentPage}
            itemsPerPage={itemsPerPage}
            itemsCount={filteredData.length}
            onPageChange={handlePageChange}
          />

        </div>
    </section>

)
}

export default MemberList;