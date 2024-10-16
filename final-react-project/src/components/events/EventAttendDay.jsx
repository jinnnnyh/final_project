function EventAttendDay({ day, attendData }) {
  // 날짜 정보
  const eventDate = attendData[0]?.attendDate || "날짜 정보 없음";

  // 참석 총인원수
  const totalParticipants = attendData.length;

  // 출석자 수
  const attendedParticipants = attendData.filter((attendee) => attendee.attendComp === 'Y').length;

  // 수료자 수
  const completedParticipants = attendData.filter((attendee) => attendee.attendComp === 'Y' && attendee.checkOutTime).length;

  // 역할 변환 함수
  const getRoleName = (role) => {
    switch (role) {
      case 'ROLE_SECRETARY':
        return '총무';
      case 'ROLE_PRESIDENT':
        return '협회장';
      case 'ROLE_REGULAR':
        return '정회원';
      default:
        return '알 수 없음';
    }
  };

  return (
    <div className="day-attend-list mb-5">
      <h5>{day} 일차 참석자 리스트</h5>
      <div className="d-flex justify-content-between small mb-3">
        <div>날짜: {eventDate}</div>
        <div>총 참석자 수: {totalParticipants}명</div>
        <div>출석자 수: {attendedParticipants}명</div>
        <div>수료자 수: {completedParticipants}명</div>
      </div>

      <table className="table table-custom">
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
        <thead>
        <tr>
          <th scope="col">번호</th>
          <th scope="col">아이디</th>
          <th scope="col">이름</th>
          <th scope="col">전화번호</th>
          <th scope="col">소속기관</th>
          <th scope="col">직위</th>
          <th scope="col">참석여부</th>
          <th scope="col">입장시간</th>
          <th scope="col">퇴장시간</th>
        </tr>
        </thead>
        <tbody>
        {attendData.map((item, idx) => (
          <tr key={item.attendId}>
            <td>{idx + 1}</td>
            <td>{item.userAccount}</td>
            <td>{item.name}</td>
            <td>{item.userPhone}</td>
            <td>{item.userDepart}</td>
            <td>{getRoleName(item.role)}</td> {/* 직위 변환 */}
            <td>{item.attendComp === 'Y' ? '참석' : '미참석'}</td>
            <td>{item.checkInTime || '미입장'}</td>
            <td>{item.checkOutTime || '미퇴장'}</td>
          </tr>
        ))}
        </tbody>
      </table>
    </div>
  );
}

export default EventAttendDay;