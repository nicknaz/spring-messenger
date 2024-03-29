import Header from "./components/Header";
import {FC, useContext, useEffect} from "react";
import {BrowserRouter as Router, Routes, Route, Navigate} from "react-router-dom";
import './css/App.css';
import WelcomePage from "./Pages/WelcomePage/WelcomePage";
import { Context } from ".";
import { observer } from "mobx-react-lite"
import MainPage from "./Pages/MainPage/MainPage";


const App: FC = () => {
  const {userStore} = useContext(Context);
  console.log(localStorage.getItem("token"));

  useEffect(() => {
    if (localStorage.getItem('token')) {
      userStore.checkAuth()
    }
  }, [])

  if (userStore.isLoading) {
    return <div>Загрузка...</div>
  }

  return (
    <Router>
      <Routes>
        <Route path="/" element={userStore.isAuth ? <MainPage /> : <WelcomePage />}/>
      </Routes>
    </Router>

    
  );
}

export default observer(App);
