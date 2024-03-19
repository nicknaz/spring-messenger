import Header from "./components/Header";
import React, {FC, useContext, useEffect, useState} from "react";
import {BrowserRouter as Router, Routes, Route} from "react-router-dom";
import './css/App.css';
import WelcomePage from "./Pages/WelcomePage/WelcomePage";
import ProfilePage from "./Pages/ProfilePage/ProfilePage";
import { Profiler } from "react";
import { Context } from ".";
import { observer } from "mobx-react-lite"


const App: FC = () => {
  const {store} = useContext(Context);
  console.log(localStorage.getItem("token"));

  useEffect(() => {
    if (localStorage.getItem('token')) {
        store.checkAuth()
    }
  }, [])

  if (store.isLoading) {
    return <div>Загрузка...</div>
  }

  if (!store.isAuth) {
    return (
      <Router>
        <Header />
        <Routes>
          <Route path="/" element={store.isAuth ? <ProfilePage /> : <WelcomePage />}/>
        </Routes>
      </Router>
    );
  }

  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={store.isAuth ? <ProfilePage /> : <WelcomePage />}/>

        <Route path="/profile" element={<ProfilePage />}/>
      </Routes>
    </Router>

    
  );
}

export default observer(App);
