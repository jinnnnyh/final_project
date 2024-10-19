
import {Route, Routes, useLocation} from "react-router-dom";
import Header from "./components/Header.jsx";
import Navigation from "./components/Navigation.jsx";
import ErrorPage from "./pages/ErroPage.jsx";
import MainPages from "./pages/MainPages.jsx";
import MemberList from "./components/member/MemberList.jsx";
import EventList from "./components/events/EventList.jsx";
import EventWrite from "./components/events/EventWrite.jsx";
import EventView from "./components/events/EventView.jsx";
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
                <Route path="/event/:eventId" element={<EventView/>}/>
                <Route path="/event/attendList/:eventId" element={<EventAttendList/>}/>
                <Route path="/member" element={<MemberList/>}/>
                <Route path="/errorPage" element={<ErrorPage/>}/>
              </Routes>
            </MainPages>
          </div>
      </div>
    </>
  )
}

export default App
