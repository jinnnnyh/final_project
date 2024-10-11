import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate, useNavigationType} from "react-router-dom";

function Login() {
    const navigate = useNavigate();
    // const navigationType = useNavigationType()

  useEffect(() => {
    if(sessionStorage != null) {
      if (sessionStorage.getItem("userRole") === 'ROLE_PRESIDENT' || sessionStorage.getItem("userRole") === 'ROLE_SECRETARY') {
        alert('이미 로그인 중입니다.');
        navigate('/');
      }
      else {
        sessionStorage.clear();
      }
    }
  }, []);


    const [userAccount, setUserAccount] = useState('');
    const [userPw, setUserPw] = useState('');
    const [userData, setUserData] = useState(null);
    const [errMsg, setErrMsg] = useState('');

    const loginHandler = async (e) => {
      e.preventDefault();


      try {
        const response = await axios.post('http://localhost:8080/user/login', {
          userAccount: userAccount,
          userPw: userPw,
        },{
          headers: {
            'Content-Type': 'application/json'
          }
        });

        if (response.data.role === 'ROLE_PRESIDENT' || response.data.role === 'ROLE_SECRETARY') {
          setUserData(response.data);
          setErrMsg(null);


          let permission = '';
          if (response.data.role === 'ROLE_PRESIDENT') {
            permission = '협회장';
          }
          else {
            permission = '총무';
          }

          sessionStorage.setItem("name", response.data.name);
          sessionStorage.setItem("permission", permission);
          sessionStorage.setItem("userId", response.data.userId);
          sessionStorage.setItem("userAccount", response.data.userAccount);
          sessionStorage.setItem("userPhone", response.data.userPhone);
          sessionStorage.setItem("userDepart", response.data.userDepart);
          sessionStorage.setItem("userRole", response.data.role);

          alert(`로그인 성공\n${response.data.name} ${permission}님 환영합니다.`);
          window.location.href = '/';
          console.log(response.data.userPw)
        }
        else {
          alert('사용자의 정보가 존재하지 않거나, 권한이 없습니다.\nID, Password, 권한을 확인해주세요.');
          window.location.href = '/login';
        }
        // console.log("성공" + response.data.userAccount + response.data.name)
      }
      catch (error) {
        alert('사용자의 정보가 존재하지 않거나, 권한이 없습니다.\nID, Password, 권한을 확인해주세요.');
      }
    }

  return (
    <section className={'container-fluid vh-100 pt-5 bg-login'}>
      <div className={'text-center mt-3'}>
        <p className={'text-black-50'}>출첵관리시스템</p>
        <h1 className={'text-point'}>Check Manager</h1>
      </div>
      <div className={'row mt-5'}>
        <div className={'col-lg-4 p-5 col-md-8 col-xs-12 mx-auto shadow bg-white'}>
          <form onSubmit={loginHandler}>
            <div>
              <label htmlFor="user-id" className={'form-label'}>아이디</label>
              <input type="text" className={'form-control py-3'} id="user-id" name="userAccount" placeholder="아이디를 입력하세요" value={userAccount} onChange={(e) => setUserAccount(e.target.value)} required/>
            </div>
            <div className={'py-4'}>
              <label htmlFor="user-pw" className={'form-label'}>비밀번호</label>
              <input type="password" className={'form-control py-3'} id="user-pw" name="password"
                     placeholder="비밀번호를 입력하세요" value={userPw} onChange={(e) => setUserPw(e.target.value)} required/>
            </div>
            <div className={'d-grid gap-2'}>
              <button type="submit" className={'btn btn-point py-3 mt-3 text-white'}>로그인</button>
            </div>
          </form>
        </div>
      </div>
    </section>
  )
}

export default Login;