import React, {FC, useEffect, useContext, useCallback} from "react";
import { Context } from "../../../..";
import '../../css/BlockWithPeoples.css';
import { IUser } from "../../../../models/IUser";
import {observer} from "mobx-react-lite";

import { FaUser } from "react-icons/fa";
import { AiFillMessage } from "react-icons/ai";
import DialogsStore from "../../../../store/DialogsStore";
import ViewerUserDialogs from "./ViewerUserDialogs";



const BlockDialogs: FC = () => {
    const {userStore} = useContext(Context);
    const dialogsStore : DialogsStore = userStore.dialogsStore;

    dialogsStore.getUserDialogs();

    return (
        <div className="dialogs">
        {
            dialogsStore.isSearches ?
            dialogsStore.usersSearchResults.map(
                (el) => (
                    <button onClick = { () => dialogsStore.chooseUser(el)}>
                        <FaUser /> {el.username} <br/>
                    </button>
                )
            )
            :
            <ViewerUserDialogs />
        }
        </div>
    )
}

export default observer(BlockDialogs) 