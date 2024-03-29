import React, {FC, useState, useContext} from "react";
import { Context } from "../../../..";
import DialogsStore from "../../../../store/DialogsStore";
import StickerService from "../../../../services/StickerService";
import '../../css/BlockMessanger.css';
import {observer} from "mobx-react-lite";

import { GiHamburgerMenu } from "react-icons/gi";
import { FaSearch, FaUser} from "react-icons/fa";
import { CiLogout } from "react-icons/ci";

const BlockWithMessages: FC = () => {
    const {userStore} = useContext(Context);
    const dialogsStore : DialogsStore = userStore.dialogsStore;

    return (
        <div className="messages">
            {
                !dialogsStore.isNewDialog ?
                dialogsStore.messages.map(
                    (el) => (
                        StickerService.messageIsSticker(el.text) ?
                        <img className="stickerMessage" id={el.senderUsername == userStore.user.username ? "sendedSticker" : "givedSticker"} src={`/stickers/` + StickerService.getStickerNumber(el.text) + `.jpg`} alt="Описание" />
                        :
                        <div className={el.senderUsername == userStore.user.username ? "sendedMessage" : "givedMessage"}>
                            {el.text}
                            <p id = "timeMessege">{el.dateTime.getHours()}:{el.dateTime.getMinutes()}</p>
                        </div>
                        
                    )
                )
                :
                <></>
            }
        </div>
    )
}

export default observer(BlockWithMessages)


                //<p id = "timeMessege">{el.dateTime.getHours()}:{el.dateTime.getMinutes()}</p>