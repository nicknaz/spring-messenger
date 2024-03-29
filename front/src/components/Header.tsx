import React, {FC, useCallback, useContext} from "react";
import { Context } from "../";
import {observer} from "mobx-react-lite";
import { checkIfStateModificationsAreAllowed } from "mobx/dist/internal";

const Header: FC = () => {
    const {userStore} = useContext(Context);


    const logout = (e: React.MouseEvent<HTMLButtonElement>) => {
        userStore.logout()
        window.location.assign("/");
    };

    return (
        <div className="header">
            <div className="logo">
                <h1>Spring Messenger</h1>
            </div>
            
            <div className="topMenu">
                {userStore.isAuth ? <button id="logout" onClick={logout}>Logout</button> : <div></div>}
            </div>
        </div>
    )
}

export default observer(Header) 