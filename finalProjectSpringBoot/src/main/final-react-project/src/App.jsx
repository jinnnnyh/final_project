
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Header from "./components/Header.jsx";
import Navigation from "./components/Navigation.jsx";
import Events from "./pages/Events.jsx";
import Member from "./pages/Member.jsx";
import ErrorPage from "./pages/ErroPage.jsx";
import MainPages from "./pages/MainPages.jsx";
import NoticeBoardList from "./components/notice/NoticeBoardList.jsx";
import NoticeBoardWrite from "./components/notice/NoticeBoardWrite.jsx";
import NoticeBoardView from "./components/notice/NoticeBoardView.jsx";


function App() {
  return (
    <BrowserRouter>
      <div className={'d-flex'}>
        <Navigation />
        <div className={'w-100'}>
          <Header />
          <MainPages>
            <Routes>
              <Route path="/notice/list" element={<NoticeBoardList/>}/>
              <Route path="/notice/write" element={<NoticeBoardWrite/>}/>
              <Route path="/notice/view" element={<NoticeBoardView/>}/>
              <Route path="/member" element={<Member />} />
              <Route path="/events" element={<Events />} />
              <Route path="/errorPage" element={<ErrorPage />} />
            </Routes>
          </MainPages>
        </div>
      </div>
    </BrowserRouter>
  )
}

export default App
