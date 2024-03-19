import React, {FC, useContext, useState} from "react";
import axios from "axios";
import { Context } from "../..";
import { observe } from "mobx";
import {observer} from "mobx-react-lite";

const ProfileUser: FC = () => {

    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const {store} = useContext(Context);

    return (
    <div className="profile-user">
        <h2>{store.user.username}</h2>
        <h4>Email: {store.user.email}</h4>
    </div>
    )


}

export default observer(ProfileUser) 