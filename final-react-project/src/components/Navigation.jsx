
import {useEffect, useState} from "react";
import {Link, useLocation} from "react-router-dom";
import events from "../pages/Events.jsx";
import member from "../pages/Member.jsx";


function Navigation() {

  const location = useLocation();
  const [activeMenu, setActiveMenu] = useState(events);
  const [activeSubMenu, setActiveSubMenu] = useState();
  const [hoveredMenu, setHoveredMenu] = useState(null);

  // URL 변경 시 activeMenu를 업데이트
  useEffect(() => {
    const currentPath = location.pathname;
    const foundMenu = menuItems.find(item => item.path === currentPath);
    if (foundMenu) {
      setActiveMenu(foundMenu.name);
      setActiveSubMenu(null); // 서브 메뉴 초기화
    }
  }, [location.pathname]);


  const handleMenuClick = (menu) => {
    setActiveMenu(menu);
  };

  const handleSubMenuClick = (subMenu) => {
    setActiveSubMenu(subMenu);
  };

  const menuItems = [
    { name: '행사관리', path: '/', icon: '/src/assets/images/ico_event_w.svg', iconActive: '/src/assets/images/ico_event.svg',
      iconHover: '/src/assets/images/ico_event.svg' },
    {
      name: '회원관리',
      path: '/member',
      icon: '/src/assets/images/ico_member_w.svg',
      iconActive: '/src/assets/images/ico_member.svg',
      iconHover: '/src/assets/images/ico_member.svg',
      subItems: [
        { name: '전체회원', path: '/member' },
        { name: '승인대기', path: '/member/permission' },
      ],
    },
    { name: '공지사항', path: '/notice', icon: '/src/assets/images/ico_notice_w.svg',
      iconActive: '/src/assets/images/ico_notice.svg', iconHover: '/src/assets/images/ico_notice.svg' },
  ];

  return (
    <div className={'nav flex-column bg-point vh-100 nav-style'}>
      <p className={'text-center pt-4'}>
        <Link to={'/'} className={'fw-bold text-white fs-4'}>
          출첵관리시스템</Link>
      </p>
      <nav>
        {menuItems.map((item) => (
          <div key={item.name}>
            <Link
              to={item.path}
              className={`nav-item ${activeMenu === item.name ? 'active' : ''}`}
              onClick={() => handleMenuClick(item.name)}
              onMouseEnter={() => setHoveredMenu(item.name)}
              onMouseLeave={() => setHoveredMenu(null)}
            >
              <img
                src={
                  activeMenu === item.name
                    ? item.iconActive
                    : hoveredMenu === item.name
                      ? item.iconHover
                      : item.icon
                }
                alt={item.name}
              />
              <span>{item.name}</span>
            </Link>
            {item.subItems && activeMenu === item.name && (
              <div className="sub-menu">
                {item.subItems.map((subItem) => (
                  <Link
                    key={subItem.name}
                    to={subItem.path}
                    className={`sub-item ${activeSubMenu === subItem.name ? 'active' : ''}`}
                    onClick={() => {
                      handleSubMenuClick(subItem.name);
                      setActiveMenu(item.name); // 상위 메뉴 활성화
                    }}
                  >
                    {subItem.name}
                  </Link>
                ))}
              </div>
            )}
          </div>
        ))}
      </nav>
      <p className={'ft-txt'}>Copyright © <br/>CheckManager. All rights reserved.</p>
    </div>
  )
}

export default Navigation;