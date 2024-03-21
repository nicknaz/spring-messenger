import React, {FC, useContext, useState} from "react";
import { Context } from "../";
import {observer} from "mobx-react-lite";

const Header: FC = () => {
    const {store} = useContext(Context);

    return (
        <div className="header">
            <div className="logo">
                <h1>Spring Messenger</h1>
            </div>
            
            <div className="topMenu">
                {store.isAuth ? <button id="logout" onClick={store.logout}>Logout</button> : <div></div>}
            </div>
        </div>
    )
}

export default observer(Header) 