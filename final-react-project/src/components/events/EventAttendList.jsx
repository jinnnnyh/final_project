import Member from "../../pages/Member.jsx";
import Events from "../../pages/Events.jsx";
import {NavLink} from "react-router-dom";
import Login from "../../pages/Login.jsx";
import {useEffect} from "react";

function EventAttendList () {
  const eventAttendData = [
    {id: 1, num:1, userAccount:'iu', name:'아이유', userPhone:'010-1111-1111', userDepart: 'AI협회', role: '일반회원', attendComp:'참석',checkInTime:'오후 2시', checkOutTime:'오후 5시' },
    {id: 2, num:2, userAccount:'js', name:'유재석', userPhone:'010-1111-1111', userDepart: 'AI협회', role: '일반회원', attendComp:'미참석',checkInTime:'오후 2시', checkOutTime:'오후 5시' },
    {id: 3, num:3, userAccount:'iu', name:'아이유', userPhone:'010-1111-1111', userDepart: 'AI협회', role: '일반회원', attendComp:'참석',checkInTime:'오후 2시', checkOutTime:'오후 5시' },
  ];

  return (
    <section>
      <Events/>
      <h4 className={'mb-5'}>참석자 현황 리스트</h4>
      <h4>행사제목입니다.</h4>
      <div className={'d-flex py-3 border-bottom justify-content-between'}>
        <div className={'w-50'}>행사기간 :<span className={'ms-3 fw-bold'}>2024.10.05.</span></div>
        <div className={'w-50'}>행사시간 :<span className={'ms-3 fw-bold'}>오후 2시 ~ 오후 5시</span></div>
      </div>

      <div>
        <table className={'table table-custom'}>
          <colgroup>
            <col width={"7%"}/>
            <col width={"10%"}/>
            <col width={"auto"}/>
            <col width={"10%"}/>
            <col width={"10%"}/>
            <col width={"10%"}/>
            <col width={"10%"}/>
            <col width={"10%"}/>
            <col width={"10%"}/>
          </colgroup>
          <thead>
          <tr>
            <th scope={'col'}>번호</th>
            <th scope={'col'}>아이디</th>
            <th scope={'col'}>이름</th>
            <th scope={'col'}>전화번호</th>
            <th scope={'col'}>소속기관</th>
            <th scope={'col'}>직위</th>
            <th scope={'col'}>참석여부</th>
            <th scope={'col'}>입장시간</th>
            <th scope={'col'}>퇴장시간</th>
          </tr>
          </thead>
          <tbody>
          {eventAttendData?.map(item => (
          <tr key={item.id} >
            <td>{item.num}</td>
            <td>{item.userAccount}</td>
            <td>{item.name}</td>
            <td>{item.userPhone}</td>
            <td>{item.userDepart}</td>
            <td>{item.role}</td>
            <td>{item.attendComp}</td>
            <td>{item.checkInTime}</td>
            <td>{item.checkOutTime}</td>
          </tr>
          ))}
          </tbody>
        </table>
        
        <div className={'d-flex justify-content-end mb-5'}>
          <NavLink to={'/'} className={'btn btn-point'}>목록보기</NavLink>
        </div>
      </div>

    </section>
  )
}

export default EventAttendList;