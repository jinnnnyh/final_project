import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import './App.css'
import Login from "./pages/Login.jsx";
import {BrowserRouter} from "react-router-dom";

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
    {/*<Login/>*/}
  </StrictMode>,
)
