function Header () {
  return (
    <header className={'pt-4 pb-2'} >
      <ul className={'d-flex justify-content-end '}>
        <li className={'me-2 text-black-50'}><small>총무</small></li>
        <li><span>아이유</span> 님</li>
        <li className={'ms-4'}>로그아웃</li>
      </ul>
    </header>
  )
}

export default Header;