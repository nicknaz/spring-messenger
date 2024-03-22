import React, {FC, useCallback, useContext} from "react";
import { Context } from "../../..";
import '../css/BlockWithPeoples.css';
import {observer} from "mobx-react-lite";

import { GiHamburgerMenu } from "react-icons/gi";
import { FaSearch, FaUser} from "react-icons/fa";
import { CiLogout } from "react-icons/ci";

const SearchBlock: FC = () => {
    const {store} = useContext(Context);

    const logout = (e: React.MouseEvent<HTMLButtonElement>) => {
        store.logout()
        window.location.assign("/");
        console.log("test");
    };

    return (
        <div className="searchBlock">
            <button id="etc">
                <GiHamburgerMenu />
                <div className="dropdown-content">
                    <button ><FaUser /> {store.user.username}</button>
                    <button onClick={logout} ><CiLogout /> Выйти</button>
                </div>
            
            </button>
            <input placeholder="Search..."></input>
            <button><FaSearch /></button>
        </div>
    )
}

export default SearchBlock