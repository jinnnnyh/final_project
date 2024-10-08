
const loginCheck = () => {
  if (sessionStorage.getItem("userId") === null) {
    alert('관리자 권한의 계정이 필요한 페이지입니다.');
    sessionStorage.clear();
    window.location.href = '/login';

    if(sessionStorage.getItem("userRole") !== "ROLE_SECRETARY") {
      if (sessionStorage.getItem("userRole") !== "ROLE_PRESIDENT") {
        alert('관리자 권한의 계정이 필요한 페이지입니다.');
        sessionStorage.clear();
        window.location.href = '/login';
      }
    }
  }
}