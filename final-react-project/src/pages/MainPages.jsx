import {useEffect} from "react";
import {useLocation, useNavigate} from "react-router-dom";

function MainPages ({children}) {

  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    // 로그인, 회원가입 페이지에서는 세션 체크하지 않도록 예외 처리
    if (location.pathname !== '/login' && location.pathname !== '/signup') {
      // 세션이 없는 경우 로그인 페이지로 리디렉션
      if (sessionStorage.getItem("userRole") === null) {
        navigate('/login');
      }

      // 세션이 있어도 권한이 없는 경우 로그인 페이지로 리디렉션
      if (sessionStorage.getItem("userRole") !== 'ROLE_PRESIDENT' && sessionStorage.getItem("userRole") !== 'ROLE_SECRETARY') {
        navigate('/login');
      }
    }
  }, [location, navigate]);

  return (
    <div>
      {children}
    </div>
  )
}

export default MainPages;