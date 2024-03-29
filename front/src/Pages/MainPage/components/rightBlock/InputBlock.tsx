import React, {FC, useState, useRef, useContext} from "react";
import { Context } from "../../../..";
import DialogsStore from "../../../../store/DialogsStore";
import StickerService from "../../../../services/StickerService";
import '../../css/BlockMessanger.css';
import {observer} from "mobx-react-lite";

import { GiHamburgerMenu, GiStickFrame } from "react-icons/gi";
import { FaSearch, FaStickerMule, FaUser} from "react-icons/fa";
import { PiStickerFill } from "react-icons/pi";
import { AiOutlineSend } from "react-icons/ai";

const InputBlock: FC = () => {
    const {userStore} = useContext(Context);
    const dialogsStore : DialogsStore = userStore.dialogsStore;
    var i : number = 0;

    const [text, setText] = useState<string>('');

    const send = (e : React.MouseEvent<HTMLButtonElement>) => {
        dialogsStore.sendMessage(text);
        setText("");
    }

    const sendSticker = (number : string) => {
        dialogsStore.sendMessage("#sticker#"+number);
        setText("");
    }

    return (
        <div className="inputMessages">
            <input value={text} id="inputMessage" onChange={(e) => setText(e.target.value)}></input>
            <button id="stickers">
                <PiStickerFill />
                <div className="dropdown-content-stickers">
                    {
                        StickerService.stickers.map((el) => (
                            <button onClick={() => sendSticker(el)}><img src={`/stickers/` + el + `.jpg`} alt="Описание" /></button>                            
                        ))
                    }
                </div>
            </button>
            <button onClick={send}> <AiOutlineSend /> </button>
        </div>
    )
}

export default observer(InputBlock)