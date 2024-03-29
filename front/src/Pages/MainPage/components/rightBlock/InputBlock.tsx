import React, {FC, useState, useRef, useContext} from "react";
import { Context } from "../../../..";
import DialogsStore from "../../../../store/DialogsStore";
import '../../css/BlockMessanger.css';
import {observer} from "mobx-react-lite";

import { GiHamburgerMenu } from "react-icons/gi";
import { FaSearch, FaUser} from "react-icons/fa";
import { CiLogout } from "react-icons/ci";
import { AiOutlineSend } from "react-icons/ai";

const InputBlock: FC = () => {
    const {userStore} = useContext(Context);
    const dialogsStore : DialogsStore = userStore.dialogsStore;

    const [text, setText] = useState<string>('');

    const send = (e : React.MouseEvent<HTMLButtonElement>) => {
        dialogsStore.sendMessage(text);
        setText("");
    }

    return (
        <div className="inputMessages">
            <input value={text} id="inputMessage" onChange={(e) => setText(e.target.value)}></input>
            <button onClick={send}> <AiOutlineSend /> </button>
        </div>
    )
}

export default observer(InputBlock)