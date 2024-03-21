import React, {FC, useContext, useState} from "react";
import { Context } from "../..";
import {observer} from "mobx-react-lite";

const LoginForm: FC = () => {

    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');

    const [status, setStatus] = useState<string>('');
    const {store} = useContext(Context);

    const login = (e : React.MouseEvent<HTMLButtonElement>) => {
        
        store.login(username, password).then(
            loginResponse => {
                if (loginResponse == 200) {
                    setStatus("Вы успешно авторизовались")
                    window.location.assign("/profile");
                } else if (loginResponse == 401) {
                    setStatus("Неправильный пароль или логин!");
                } else {
                    setStatus("Ошибка на стороне сервера");
                }
            }
        )
        
    }

    return (
    <form className="loginForm">
        <h4>Log In</h4>
        <input placeholder="login" onChange={(e) => setUsername(e.target.value)}></input>
        <input placeholder="password" type="password" onChange={(e) => setPassword(e.target.value)}></input>
        <button type="button" onClick={login} >Log In</button>
        <h4>{status}</h4>
    </form>
    )


}

export default observer(LoginForm) 