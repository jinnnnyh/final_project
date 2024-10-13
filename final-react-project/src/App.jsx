
import {BrowserRouter, Route, Routes, useLocation} from "react-router-dom";
import Header from "./components/Header.jsx";
import Navigation from "./components/Navigation.jsx";
import ErrorPage from "./pages/ErroPage.jsx";
import MainPages from "./pages/MainPages.jsx";
import NoticeBoardList from "./components/notice/NoticeBoardList.jsx";
import NoticeBoardWrite from "./components/notice/NoticeBoardWrite.jsx";
import NoticeBoardView from "./components/notice/NoticeBoardView.jsx";
import MemberList from "./components/member/MemberList.jsx";
import MemberEdit from "./components/member/MemberEdit.jsx";
import EventList from "./components/events/EventList.jsx";
import EventWrite from "./components/events/EventWrite.jsx";
import EventView from "./components/events/EventView.jsx";
import MemberPermission from "./components/member/MemberPermission.jsx";
import EventAttendList from "./components/events/EventAttendList.jsx";
import Login from "./pages/Login.jsx";
import Signup from "./pages/test/Signup.jsx";


function App() {

  const location = useLocation();

  return (
    <>
      <Routes >
        <Route path="/login" element={<Login />}/>
        <Route path="/signup" element={<Signup />} />
      </Routes>
      <div className={'d-flex'}>
          {/* 왼쪽 네비게이션 */}
          {/*<Navigation/>*/}
        {location.pathname !== '/login' && location.pathname !== '/signup' && <Navigation/>}
          <div className={'container-fluid'} style={{marginLeft:"330px", marginRight:"50px", marginBottom:"100px"}}>
            {location.pathname !== '/login' && location.pathname !== '/signup' && <Header/>}
            {/*<Header/>*/}
            <MainPages>
              <Routes>
                <Route path="/" element={<EventList/>}/>
                <Route path="/event/write" element={<EventWrite/>}/>
                {/*<Route path="/events/view" element={<EventView/>}/>*/}
                <Route path="/event/:eventId" element={<EventView/>}/>
                <Route path="/event/attend" element={<EventAttendList/>}/>
                <Route path="/member" element={<MemberList/>}/>
                <Route path="/member/edit" element={<MemberEdit/>}/>
                <Route path="/member/permission" element={<MemberPermission/>}/>
                <Route path="/notice" element={<NoticeBoardList/>}/>
                <Route path="/notice/write" element={<NoticeBoardWrite/>}/>
                <Route path="/notice/view" element={<NoticeBoardView/>}/>
                <Route path="/errorPage" element={<ErrorPage/>}/>
              </Routes>
            </MainPages>
          </div>
      </div>
    </>
  )
}

export default App
