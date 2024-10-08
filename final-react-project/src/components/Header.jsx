function Header () {

  const logoutHandler = () => {
    const confirmed = confirm("로그아웃 합니까?");

    if (confirmed === true) {
      sessionStorage.clear();
      window.location.href = '/login'
    }
  }

  return (
    <header className={'pt-4 pb-2'} >
      <ul className={'d-flex justify-content-end '}>
        <li className={'me-2 text-black-50'}><small>{sessionStorage.getItem("permission")}</small></li>
        <li><span>{sessionStorage.getItem("name")}</span> 님</li>
        <li className={'ms-4'}><a href={'#'} onClick={logoutHandler}>로그아웃</a></li>
      </ul>
    </header>
  )
}

export default Header;