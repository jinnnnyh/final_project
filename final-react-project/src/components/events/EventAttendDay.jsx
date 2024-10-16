import { useState } from 'react';
import { isLate, isEarlyLeave } from '../../utils/util.js';

function EventAttendDay({ day, attendData, eventDate, eventStartTime, eventEndTime }) {
  const [searchName, setSearchName] = useState('');
  const [selectedRole, setSelectedRole] = useState('');
  const [selectedLate, setSelectedLate] = useState('');
  const [selectedEarlyLeave, setSelectedEarlyLeave] = useState('');

  const totalAttendees = attendData.length;
  const attendeesPresent = attendData.filter(item => item.checkOutTime != null).length;
  const attendeesCompleted = attendData.filter(item => item.attendComp === 'Y').length;

  const filteredData = attendData.filter(item => {
    const matchesName = item.name.includes(searchName);
    const matchesRole = selectedRole ? item.role === selectedRole : true;

    const matchesLate =
      selectedLate === 'Y' ? (item.checkInTime && isLate(item.checkInTime, eventStartTime)) :
        selectedLate === 'N' ? (item.checkInTime && !isLate(item.checkInTime, eventStartTime)) :
          selectedLate === '' ? true : // 기본값
            selectedLate === 'none' ? !item.checkInTime : false; // 해당없음: 미입장

    const matchesEarlyLeave =
      selectedEarlyLeave === 'Y' ? (item.checkOutTime && isEarlyLeave(item.checkOutTime, eventEndTime)) :
        selectedEarlyLeave === 'N' ? (item.checkOutTime && !isEarlyLeave(item.checkOutTime, eventEndTime)) :
          selectedEarlyLeave === '' ? true : // 기본값
            selectedEarlyLeave === 'none' ? !item.checkOutTime : false; // 해당없음: 미퇴장

    return matchesName && matchesRole && matchesLate && matchesEarlyLeave;
  });

  const handleSearchChange = (e) => {
    setSearchName(e.target.value);
  };

  const handleRoleChange = (e) => {
    setSelectedRole(e.target.value);
  };

  const handleLateChange = (e) => {
    setSelectedLate(e.target.value);
  };

  const handleEarlyLeaveChange = (e) => {
    setSelectedEarlyLeave(e.target.value);
  };

  return (
    <div className="day-attend-list mb-4 pt-3 border-bottom border-1 border-dark-subtle">
      <div className="d-flex justify-content-between align-items-center mb-2">
        <span><strong>{day} 일차 ({eventDate})</strong></span>
        <span>신청자: {totalAttendees}</span>
        <span>출석자: {attendeesPresent}</span>
        <span>당일 수료자: {attendeesCompleted}</span>
      </div>

      <div className="d-flex flex-wrap mb-3">
        <input
          type="text"
          placeholder="이름 검색"
          value={searchName}
          onChange={handleSearchChange}
          className="form-control me-2 mb-2"
          style={{ width: '200px' }}
        />
        <select onChange={handleRoleChange} className="form-select me-2 mb-1" style={{ width: '150px' }}>
          <option value="">직위</option>
          <option value="ROLE_SECRETARY">총무</option>
          <option value="ROLE_PRESIDENT">협회장</option>
          <option value="ROLE_REGULAR">정회원</option>
          <option value="ROLE_ASSOCIATE">준회원</option>
          <option value="ROLE_WITHDRAWN">탈퇴회원</option>
        </select>
        <select onChange={handleLateChange} className="form-select me-2 mb-1" style={{ width: '150px' }}>
          <option value="">지각 여부</option>
          <option value="Y">지각</option>
          <option value="N">정상</option>
          <option value="none">미입장</option>
        </select>
        <select onChange={handleEarlyLeaveChange} className="form-select me-2 mb-1" style={{ width: '150px' }}>
          <option value="">조퇴 여부</option>
          <option value="Y">조퇴</option>
          <option value="N">정상</option>
          <option value="none">미퇴장</option>
        </select>
      </div>

      <div className="table-container">
        <table className="table table-custom table-hover">
          <colgroup>
            <col width="7%" />
            <col width="10%" />
            <col width="auto" />
            <col width="10%" />
            <col width="10%" />
            <col width="10%" />
            <col width="10%" />
            <col width="10%" />
            <col width="10%" />
          </colgroup>
          <thead className="table-top-fix">
          <tr>
            <th scope="col">번호</th>
            <th scope="col">아이디</th>
            <th scope="col">이름</th>
            <th scope="col">전화번호</th>
            <th scope="col">직위</th>
            <th scope="col">입장 시간</th>
            <th scope="col">퇴장 시간</th>
            <th scope="col">지각 여부</th>
            <th scope="col">조퇴 여부</th>
          </tr>
          </thead>
          <tbody>
          {filteredData.map((item, idx) => (
            <tr key={item.attendId}>
              <td>{idx + 1}</td>
              <td>{item.userAccount}</td>
              <td>{item.name}</td>
              <td>{item.userPhone}</td>
              <td>
                {item.role === 'ROLE_SECRETARY' && '총무'}
                {item.role === 'ROLE_PRESIDENT' && '협회장'}
                {item.role === 'ROLE_REGULAR' && '정회원'}
                {item.role === 'ROLE_ASSOCIATE' && '준회원'}
                {item.role === 'ROLE_WITHDRAWN' && '탈퇴회원'}
              </td>
              <td>{item.checkInTime || '미입장'}</td>
              <td>{item.checkOutTime || '미퇴장'}</td>
              <td>
                {item.checkInTime
                  ? isLate(item.checkInTime, eventStartTime) ? '지각' : '정상'
                  : ''}
              </td>
              <td>
                {item.checkOutTime
                  ? isEarlyLeave(item.checkOutTime, eventEndTime) ? '조퇴' : '정상'
                  : ''}
              </td>
            </tr>
          ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default EventAttendDay;