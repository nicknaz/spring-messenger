import React, {FC, useEffect, useContext, useCallback} from "react";
import { Context } from "../../../..";
import '../../css/BlockWithPeoples.css';
import { IUser } from "../../../../models/IUser";
import {observer} from "mobx-react-lite";

import { FaUser } from "react-icons/fa";
import { AiFillMessage } from "react-icons/ai";
import DialogsStore from "../../../../store/DialogsStore";

const ViewerUserDialogs: FC = () => {
    const {userStore} = useContext(Context);
    const dialogsStore : DialogsStore = userStore.dialogsStore;
    
    return (
        <>
        {
            dialogsStore.userDialogs.map(
                (el) => (
                    <button onClick = { () => dialogsStore.chooseDialog(el)}>
                        <FaUser /> {el.firstUser.username == userStore.user.username ? el.secondUser.username : el.firstUser.username} <br/>
                        <AiFillMessage /> {el.lastMessage.senderUsername} : {el.lastMessage.text.substring(0, 20)} <br/>
                    </button>
                )
            )
        }
        </>
        
    )

}

export default observer(ViewerUserDialogs) 