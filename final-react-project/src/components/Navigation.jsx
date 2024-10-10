
import {useEffect, useState} from "react";
import {Link, useLocation} from "react-router-dom";
import events from "../pages/Events.jsx";
import member from "../pages/Member.jsx";

function Navigation() {

  const [activeMenu, setActiveMenu] = useState(null);
  const [activeSubMenu, setActiveSubMenu] = useState(0);

  const menus = [
    {
      id: 1,
      name: '행사관리',
      icon: '/src/assets/images/ico_event_w.svg',
      activeIcon: '/src/assets/images/ico_event.svg',
      path: '/',
      subMenus: [],
    },
    {
      id: 2,
      name: '회원관리',
      icon: '/src/assets/images/ico_member_w.svg',
      activeIcon: '/src/assets/images/ico_member.svg',
      path: '/member',
      subMenus: [
        { path: '/member', name: '전체회원' },
        { path: '/member/permission', name: '승인대기' },
      ],
    },
    {
      id: 3,
      name: '공지사항',
      icon: '/src/assets/images/ico_notice_w.svg',
      activeIcon: '/src/assets/images/ico_notice.svg',
      path: '/notice',
      subMenus: [],
    },
  ];

  const handleMenuClick = (id) => {
    setActiveMenu(activeMenu === id ? null : id);
    setActiveSubMenu(0);
  };

  const handleSubMenuClick = (index) => {
    setActiveSubMenu(index);
  };

  const handleLogoClick = () => {
    setActiveMenu(menus[0].id); // 첫 번째 메뉴를 활성화
  };

  return (
    <div className={'nav flex-column bg-point vh-100 nav-style'}>
      <p className={'text-center pt-4'}>
        <Link to={'/'} className={'fw-bold text-white fs-4'} onClick={handleLogoClick}>
          출첵관리시스템</Link>
      </p>

      <nav className="navbar">
        <ul>
          {menus.map((menu) => (
            <li key={menu.id}>
              <Link to={menu.path} className={`menu-item ${activeMenu === menu.id ? 'active' : ''}`}
                    onClick={() => handleMenuClick(menu.id)} tabIndex={0}>
                <img
                  src={activeMenu === menu.id ? `${menu.activeIcon}` : menu.icon}
                  alt={`${menu.name} 아이콘`}
                />
                {menu.name}
              </Link>
              {activeMenu === menu.id && (
                <ul className="submenu">
                  {menu.subMenus.map((subMenu, index) => (
                    <li
                      key={index}
                      className={activeSubMenu === index ? 'active' : ''}
                      onClick={() => handleSubMenuClick(index)}
                    >
                      <Link to={subMenu.path}>
                        {subMenu.name}
                      </Link>
                    </li>
                  ))}
                </ul>
              )}
            </li>
          ))}
        </ul>
      </nav>

      <p className={'ft-txt'}>Copyright © <br/>CheckManager. All rights reserved.</p>
    </div>
  )
}

export default Navigation;