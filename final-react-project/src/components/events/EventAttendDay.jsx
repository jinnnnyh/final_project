import { isLate, isEarlyLeave } from '../../utils/util.js';

function EventAttendDay({ day, attendData, eventDate, eventStartTime, eventEndTime }) {

  const totalAttendees = attendData.length;

  const attendeesPresent = attendData.filter(
    item => item.checkOutTime != null
  ).length;

  const attendeesCompleted = attendData.filter(item => item.attendComp === 'Y').length;

  return (
    <div className="day-attend-list mb-4 pt-3 border-bottom border-1 border-dark-subtle">
      <div className="d-flex justify-content-between align-items-center mb-2">
        <span><strong>{day} 일차 ({eventDate})</strong></span>
        <span>신청자 : {totalAttendees}</span>
        <span>출석자 : {attendeesPresent}</span>
        <span>당일 수료자 : {attendeesCompleted}</span>
      </div>
      <div className="table-container">
        <table className="table table-custom table-hover table-container">
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
          <thead className={'table-top-fix'}>
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
          {attendData.map((item, idx) => (
            <tr key={item.attendId}>
              <td>{idx + 1}</td>
              <td>{item.userAccount}</td>
              <td>{item.name}</td>
              <td>{item.userPhone}</td>
              <td>
                {item.role === 'ROLE_SECRETARY' && '총무'}
                {item.role === 'ROLE_PRESIDENT' && '협회장'}
                {item.role === 'ROLE_REGULAR' && '정회원'}
              </td>
              <td>{item.checkInTime || '미입장'}</td>
              <td>{item.checkOutTime || '미퇴장'}</td>
              <td>
                {item.checkInTime
                  ? isLate(item.checkInTime, eventStartTime) ? '지각' : '해당없음'
                  : '-'}
              </td>
              <td>
                {item.checkOutTime
                  ? isEarlyLeave(item.checkOutTime, eventEndTime) ? '조퇴' : '해당없음'
                  : '-'}
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