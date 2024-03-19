import React, {FC, useContext, useState} from "react";
import axios from "axios";
import { Context } from "../..";
import { observe } from "mobx";
import {observer} from "mobx-react-lite";

const LoginForm: FC = () => {

    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const {store} = useContext(Context);

    return (
    <form className="loginForm">
        <h4>Log In</h4>
        <input placeholder="login" onChange={(e) => setUsername(e.target.value)}></input>
        <input placeholder="password" type="password" onChange={(e) => setPassword(e.target.value)}></input>
        <button type="button" onClick={() => store.login(username, password)} >Log In</button>
    </form>
    )


}

export default observer(LoginForm) 