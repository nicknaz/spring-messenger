import React, {FC, useCallback, useContext} from "react";
import { Context } from "../../../..";
import DialogsStore from "../../../../store/DialogsStore";
import '../../css/BlockWithPeoples.css';
import {observer} from "mobx-react-lite";

import { GiHamburgerMenu } from "react-icons/gi";
import { FaSearch, FaUser} from "react-icons/fa";
import { CiLogout } from "react-icons/ci";

const SearchBlock: FC = () => {
    const {userStore} = useContext(Context);
    const dialogsStore : DialogsStore = userStore.dialogsStore;

    const changedSearch = (e : React.ChangeEvent<HTMLInputElement>) => {
        const value : string = e.target.value;
        if (value.length > 0) {
            dialogsStore.setIsSearched(true);
            if (value.length > 2) {
                dialogsStore.searchUsers(value);
            }
        } else {
            dialogsStore.setIsSearched(false);
            dialogsStore.setUsersSearchResults([]);
        }   
    }

    const logout = (e: React.MouseEvent<HTMLButtonElement>) => {
        userStore.logout()
        window.location.assign("/");
        console.log("test");
    };

    return (
        <div className="searchBlock">
            <button id="etc">
                <GiHamburgerMenu />
                <div className="dropdown-content">
                    <button ><FaUser /> {userStore.user.username}</button>
                    <button onClick={logout} ><CiLogout /> Выйти</button>
                </div>
            
            </button>
            <input onChange={(e) => changedSearch(e)} placeholder="Search..."></input>
            <button><FaSearch /></button>
        </div>
    )
}

export default observer(SearchBlock)