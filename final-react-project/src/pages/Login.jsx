function Login() {
  return (
    <section className={'container-fluid vh-100 pt-5 bg-login'}>
      <div className={'text-center mt-3'}>
        <p className={'text-black-50'}>출첵관리시스템</p>
        <h1 className={'text-point'}>Check Manager</h1>
      </div>
      <div className={'row mt-5'}>
        <div className={'col-lg-4 col-md-8 col-xs-12 mx-auto shadow bg-white'}>
          <form action="" method="post" className={'px-4 py-5'}>
            <div>
              <label htmlFor="user-id" className={'form-label'}>아이디</label>
              <input type="text" className={'form-control py-3'} id="user-id" name="username" placeholder="아이디를 입력하세요"/>
            </div>
            <div className={'py-4'}>
              <label htmlFor="user-pw" className={'form-label'}>비밀번호</label>
              <input type="password" className={'form-control py-3'} id="user-pw" name="password"
                     placeholder="비밀번호를 입력하세요"/>
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