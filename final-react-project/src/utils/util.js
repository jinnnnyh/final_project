export function isLate(checkInTime, eventStartTime) {
  const checkIn = new Date(`1970-01-01T${checkInTime}`); // 기본 날짜 사용
  const start = new Date(`1970-01-01T${eventStartTime}`);
  return checkIn > start; // 체크인 시간이 시작 시간보다 늦은지 확인
}

export function isEarlyLeave(checkOutTime, eventEndTime) {
  const checkOut = new Date(`1970-01-01T${checkOutTime}`); // 기본 날짜 사용
  const end = new Date(`1970-01-01T${eventEndTime}`);
  return checkOut < end; // 체크아웃 시간이 종료 시간보다 이른지 확인
}