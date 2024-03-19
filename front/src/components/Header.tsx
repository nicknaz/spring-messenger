import React, {FC, useContext, useState} from "react";
import axios from "axios";
import { Context } from "../";
import { observe } from "mobx";
import {observer} from "mobx-react-lite";

const Header: FC = () => {
    const {store} = useContext(Context);

    return (
        <header className="header">
            <h1>Spring Messenger</h1>
            {store.isAuth ? <button onClick={store.logout}>Logout</button> : <div></div>}
        </header>
    )
}

export default observer(Header) 