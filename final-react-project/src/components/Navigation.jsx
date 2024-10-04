import {Link} from "react-router-dom";
import {useState} from "react";


function Navigation() {
  const [activeKey, setActiveKey] = useState('notice'); // 기본 선택 메뉴

  const handleSelect = (eventKey) => {
    setActiveKey(eventKey);
  };


  return (
    <div className={'col col-2 vh-100 bg-point'}>
      <p className={'text-center pt-4'}>
        <Link to={'/notice/list'} className={'fw-bold text-white fs-4'}>
          출석매니저시스템</Link>
      </p>
      <nav>
        <ul className={'nav-style'}>
          <li className={activeKey === 'notice' ? 'active-link' : ''}>
            <Link to="/notice" onClick={() => handleSelect('notice')}>
              공지사항
            </Link>
          </li>
          <li className={activeKey === 'member' ? 'active-link' : ''}>
            <Link to="/member" onClick={() => handleSelect('member')}>
              <i className="ico-member"></i> 회원관리
            </Link>
          </li>
          <li className={activeKey === 'events' ? 'active-link' : ''}>
            <Link to="/events" onClick={() => handleSelect('events')}>
              <i className="ico-events"></i> 행사관리
            </Link>
          </li>
        </ul>
      </nav>
      <p className={'ft-txt'}>Copyright © <br/>checkmanager. All rights reserved.</p>
    </div>

  )
}

export default Navigation;