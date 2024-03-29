import React, {FC, useContext, useState} from "react";
import { Context } from "../..";
import { observer } from "mobx-react-lite";
import RegistrationResponse from "../../models/RegistrationResponse";


const RegistrationForm: FC = () => {

    const [username, setUsername] = useState<string>('');
    const [email, setEmail] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [secondPassword, setSecondPassword] = useState<string>('');

    const [status, setStatus] = useState<string>('');
    const {userStore} = useContext(Context);

    const registration = (e : React.MouseEvent<HTMLButtonElement>) => {
        if (password != secondPassword ) {
            setStatus("Пароли не совпадают!")
        } else {
            //reg : RegistrationResponse = await store.registration(username, password, email);
            
            userStore.registration(username, password, email).then(
                regResponse => {
                    if (regResponse.status == 200) {
                        setStatus("Вы успешно зарегистрировались")
                        window.location.assign("/");
                    } else {
                        setStatus(regResponse.messege);
                    }
                }
            )
        }
    }

    return (
    <form className="loginForm">
        <h4>Log In</h4>
        <input placeholder="name" onChange={(e) => setUsername(e.target.value)}></input>
        <input placeholder="email" onChange={(e) => setEmail(e.target.value)}></input>
        <input placeholder="password" type="password" onChange={(e) => setPassword(e.target.value)}></input>
        <input placeholder="password" type="password" onChange={(e) => setSecondPassword(e.target.value)}></input>
        <button type="button" onClick={registration} >Registration</button>
        <h4>{status}</h4>
    </form>
    )
    

}

export default observer(RegistrationForm)