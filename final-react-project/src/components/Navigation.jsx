
import {useEffect, useState} from "react";
import {Link, NavLink, useLocation} from "react-router-dom";
import events from "../pages/Events.jsx";
import member from "../pages/Member.jsx";


function Navigation() {
  const navItems = [
    { path: '/', label: '행사관리', icon: '/src/assets/images/ico_event_w.svg' },
    { path: '/member', label: '회원관리', icon: '/src/assets/images/ico_member_w.svg' },
  ];

  const [activeLink, setActiveLink] = useState('/');

  // 로컬 스토리지에서 선택된 메뉴 가져옴
  useEffect(() => {
    const activeMenu = localStorage.getItem('setActiveLink');
    if (activeMenu) {
      setActiveLink(activeMenu);
    }
  }, []);

  // 메뉴 선택 시 호출되는 함수
  const handleMenuSelect = (path) => {
    setActiveLink(path);
    localStorage.setItem('activeLink', path);
  };

  // 로고 클릭 시 호출되는 함수
  const handleLogoClick = () => {
    setActiveLink('/'); // 첫 번째 메뉴 선택
    localStorage.removeItem('activeLink'); // 로컬 스토리지 해제
  };


  return (
    <div className={'nav flex-column bg-point vh-100 nav-style'}>
      <p className={'text-center pt-4'}>
        <Link to={'/'} className={'fw-bold text-white fs-4'} onClick={handleLogoClick} >
          출첵관리시스템</Link>
      </p>

      <nav>
        {navItems.map((item) => (
          <NavLink
            key={item.path}
            to={item.path}
            className={`nav-item ${activeLink === item.path ? 'active' : ''}`}
            onClick={() => handleMenuSelect(item.path)}
          >
            <img
              src={item.icon}
              alt={item.label}
              className="nav-icon"
            />
            <span className="nav-label">{item.label}</span>
          </NavLink>
        ))}
      </nav>

      <p className={'ft-txt'}>Copyright © <br/>CheckManager. All rights reserved.</p>
    </div>
  )
}

export default Navigation;