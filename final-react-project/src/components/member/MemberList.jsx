import Member from "../../pages/Member.jsx";
import {useEffect, useState} from "react";
import axios from "axios";
import Pagination from "../common/Pagination.jsx";
import {useParams} from "react-router-dom";
import Confirm from "../common/Confirm.jsx";


function MemberList () {

  const [memberList, setMemberList] = useState([]);
  const [memberListData, setMemberListData] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(8); // 한 페이지당 항목 수
  const [selectedOption, setSelectedOption] = useState('all'); // 기본값 'all'
  const [selectedItemId, setSelectedItemId] = useState(null);
  const { userId } = useParams();
  const [isModalOpen, setModalOpen] = useState(false);

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

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleSelectChange = (event) => {
    setSelectedOption(event.target.value);
    setCurrentPage(1);// 옵션 변경 시 페이지를 1로 리셋
  };

  // 체크박스 하나만 선택 가능
  const handleCheck = (userId) => {
    if (selectedItemId !== null && selectedItemId !== userId) {
      alert("하나의 항목만 선택할 수 있습니다.");
      return;
    }
    setSelectedItemId(selectedItemId === userId ? null : userId);
  };


  // 회원탈퇴
  const handleDelete = async () => {
    if (selectedItemId === null) {
      alert("삭제할 항목을 선택해주세요.");
      return;
    }
    const confirmed = window.confirm('회원을 탈퇴처리 하시겠습니까?');

    try {
      await axios.delete(`http://localhost:8080/user/signOut/${userId}`);
      setMemberList(memberList.filter(item => item.userId !== selectedItemId));
      setSelectedItemId(null);
      alert("항목이 삭제되었습니다.");
    } catch (error) {
      console.error("삭제 오류:", error);
      alert("삭제 중 오류가 발생했습니다.");
    }
  };

  // 승인여부
  const handleApproval = () => {
    // 기본 승인대기 (1)
    // 클릭 시 승인 confirm 모달창 =>
    // 확인 : 승인완료 (2) 버튼 명, 색상 변경
    // 거부 : 승인거부 (3) 버튼 명, 색상 변경
    // 협회장 아닐 시 권한이 없습니다. 모달창띄움
    console.log('승인되었습니다!');
  };


  return (
    <section>
      <Member/>
      <div className={'d-flex justify-content-end mb-3'}>
        <p className={'text-danger mt-1 me-3'}>※ 직위별 구분 가능</p>
        <form action="">
          <select value={selectedOption} onChange={handleSelectChange} className={'px-2 py-1'}>
            <option value="all" >전체</option>
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
            <col width={"6%"}/>
            <col width={"6%"}/>
            <col width={"auto"}/>
            <col width={"15%"}/>
            <col width={"15%"}/>
            <col width={"15%"}/>
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
            <th scope={'col'}>승인여부</th>
          </tr>
          </thead>
          <tbody>

          {memberItems.map((item) => (
                <tr key={item.userId}>
                  <td>
                    <div className="form-check">
                      <input className="form-check-input"
                        type="checkbox"
                        checked={selectedItemId === item.userId}
                        onChange={() => handleCheck(item.userId)}
                      />
                      <label className="form-check-label" htmlFor="checkbox"></label>
                    </div>
                  </td>
                  <td>{item.userId}</td>
                  <td>{item.userAccount}</td>
                  <td>{item.name}</td>
                  <td>{item.userPhone}</td>
                  <td>{item.userDepart}</td>
                  <td>{item.role}</td>
                  <td>
                    <button type={'button'} className={'btn btn-outline-point py-1'} onClick={() => setModalOpen(true)}>승인대기</button>
                    {isModalOpen && (
                      <Confirm
                        message="회원을 정회원으로 승인하시겠습니까?"
                        onConfirm={handleApproval}
                        onCancel={() => setModalOpen(false)}
                      />
                    )}
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

        <div className={'d-flex mt-3 justify-content-end'}>
          <button type={'button'} className={'btn btn-outline-danger'} onClick={handleDelete}>회원탈퇴</button>
        </div>
      </div>

    </section>
  )
}

export default MemberList;