import {useEffect} from "react";
import {useNavigate} from "react-router-dom";

function MainPages ({children}) {

  const navigate = useNavigate();
  useEffect(() => {

    if (sessionStorage === null) {
      navigate('/login');
    }
    if (sessionStorage.getItem("userRole") !== 'ROLE_PRESIDENT' && sessionStorage.getItem("userRole") !== 'ROLE_SECRETARY') {
      navigate('/login');
    }
  }, []);

  return (
    <div>
      {children}
    </div>
  )
}

export default MainPages;