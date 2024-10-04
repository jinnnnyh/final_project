
import {BrowserRouter, Route, Routes } from "react-router-dom";
import Header from "./components/Header.jsx";
import Navigation from "./components/Navigation.jsx";
import Events from "./pages/Events.jsx";
import Member from "./pages/Member.jsx";
import ErrorPage from "./pages/ErroPage.jsx";
import MainPages from "./pages/MainPages.jsx";
import NoticeBoardList from "./components/notice/NoticeBoardList.jsx";
import NoticeBoardWrite from "./components/notice/NoticeBoardWrite.jsx";
import NoticeBoardView from "./components/notice/NoticeBoardView.jsx";
import TempComponent from "./components/temp/TempComponent.jsx";
import MemberList from "./components/member/MemberList.jsx";
import EventList from "./components/events/EventList.jsx";
import MemberWrite from "./components/member/MemberWrite.jsx";
import EventWrite from "./components/events/EventWrite.jsx";
import EventView from "./components/events/EventView.jsx";



function App() {
  return (
    <BrowserRouter>
      <div className={'d-flex'}>
        <Navigation/>
        <div className={'w-100'}>
          <Header/>
          <MainPages>

            <Routes>
              <Route path="/notice" element={<NoticeBoardList/>}/>
              <Route path="/notice/write" element={<NoticeBoardWrite/>}/>
              <Route path="/notice/view" element={<NoticeBoardView/>}/>
              <Route path="/member" element={<MemberList/>}/>
              <Route path="/member/write" element={<MemberWrite/>}/>
              <Route path="/events" element={<EventList/>}/>
              <Route path="/events/write" element={<EventWrite/>}/>
              <Route path="/events/view" element={<EventView/>}/>
              <Route path="/errorPage" element={<ErrorPage/>}/>
              <Route path="/temp" element={<TempComponent/>}/>
            </Routes>
          </MainPages>
        </div>
      </div>
    </BrowserRouter>
  )
}

export default App
