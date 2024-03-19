import React, {FC, useContext, useState} from "react";
import axios from "axios";
import { Context } from "../..";
import { observer } from "mobx-react-lite";


const RegistrationForm: FC = () => {

    const [username, setUsername] = useState<string>('');
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [secondPassword, setSecondPassword] = useState<string>('');
    const {store} = useContext(Context);

    return (
    <form className="loginForm">
        <h4>Log In</h4>
        <input placeholder="name" onChange={(e) => setUsername(e.target.value)}></input>
        <input placeholder="email" onChange={(e) => setEmail(e.target.value)}></input>
        <input placeholder="password" type="password" onChange={(e) => setPassword(e.target.value)}></input>
        <input placeholder="password" type="password" onChange={(e) => setSecondPassword(e.target.value)}></input>
        <button type="button" onClick={() => store.registration(username, password)} >Registration</button>
    </form>
    )
    

}

export default observer(RegistrationForm)