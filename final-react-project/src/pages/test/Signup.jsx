import axios from "axios";
import {useState} from "react";
import {useNavigate} from "react-router-dom";

function Signup() {

  const [userAccount, setUserAccount] = useState('');
  const [name, setName] = useState('');
  const [userPhone, setUserPhone] = useState('');
  const [userDepart, setUserDepart] = useState('');
  const [userPermission, setUserPermission] = useState('');
  const [userPw, setUserPw] = useState('');

  const navigate = useNavigate();

  const signupHandler = (e) => {
    e.preventDefault()
    axios.put('http://localhost:8080/user/signup', {
        userAccount: userAccount,
        userPw: userPw,
        userPermission: userPermission,
        userPhone: userPhone,
        userDepart: userDepart,
        name: name,
      },
      {
        headers: {
          'Content-Type': 'application/json'
         }
      }).then(() => {
        alert('성공');
        navigate('/login');
    })
      .catch(e => {
        alert('실패' + e.message);
        window.location.reload();
      })

  }


  return (
    <section className={'container-fluid vh-100 pt-5 bg-login'}>
      <div className={'text-center mt-3'}>
        <p className={'text-black-50'}>출첵관리시스템 회원가입</p>
        <h1 className={'text-point'}>Check Manager Sign-in</h1>
        <p className={'text-black-50'}>해당 페이지는 실제 서비스 중에는 접근할 수 없는 페이지입니다.</p>
      </div>
      <div className={'row mt-5'}>
        <div className={'col-lg-4 col-md-8 col-xs-12 mx-auto shadow bg-white'}>
          <form onSubmit={signupHandler}>
            <div>
              <label htmlFor="user-id" className={'form-label'}>아이디</label>
              <input type="text" className={'form-control py-3'} id="user-id" name="userAccount"
                     placeholder="아이디를 입력하세요" value={userAccount} onChange={(e) => setUserAccount(e.target.value)}
                     required/>
            </div>
            <div className={'py-4'}>
              <label htmlFor="user-pw" className={'form-label'}>비밀번호</label>
              <input type="password" className={'form-control py-3'} id="user-pw" name="password"
                     placeholder="비밀번호를 입력하세요" value={userPw} onChange={(e) => setUserPw(e.target.value)} required/>
            </div>
            <div>
              <label htmlFor="user-name" className={'form-label'}>이름</label>
              <input type="text" className={'form-control py-3'} id="user-name" name="userName"
                     placeholder="이름을 입력하세요" value={name} onChange={(e) => setName(e.target.value)}
                     required/>
            </div>
            <div>
              <label htmlFor="user-phone" className={'form-label'}>연락처</label>
              <input type="text" className={'form-control py-3'} id="user-phone" name="userPhone"
                     placeholder="연락처를 입력하세요" value={userPhone} onChange={(e) => setUserPhone(e.target.value)}
                     required/>
            </div>
            <div>
              <label htmlFor="user-depart" className={'form-label'}>소속</label>
              <input type="text" className={'form-control py-3'} id="user-depart" name="userDepart"
                     placeholder="연락처를 입력하세요" value={userDepart} onChange={(e) => setUserDepart(e.target.value)}
                     required/>
            </div>
            <div className={'py-4'}>
              <label htmlFor="user-permission" className={'form-label'}>권한</label>
              <select className={'form-select py-3'} id="user-permission" name="userPermission"
                      value={userPermission} onChange={(e) => setUserPermission(e.target.value)} required>
                <option value="협회장">협회장</option>
                <option value="총무">총무</option>
                <option value="정회원">정회원</option>
                <option value="준회원">준회원</option>
              </select>
            </div>
            <div className={'d-grid gap-2'}>
              <button type="submit" className={'btn btn-point py-3 mt-3 text-white'}>데이터 추가</button>
            </div>
          </form>
        </div>
      </div>
    </section>
  )
}

export default Signup;