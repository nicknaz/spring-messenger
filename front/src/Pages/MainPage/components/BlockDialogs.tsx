import React, {FC, useCallback, useContext} from "react";
import { Context } from "../../..";
import '../css/BlockWithPeoples.css';
import { IUser } from "../../../models/IUser";
import {observer} from "mobx-react-lite";

import { FaUser } from "react-icons/fa";
import { AiFillMessage } from "react-icons/ai";

const BlockDialogs: FC = () => {
    const {store} = useContext(Context);

    const users : String[] = ["Jack", "Kek", "Wura", "Kek", "Wura", "Kek", "Wura", "Kek", "Wura", "Kek", "Wura", "Kek", "Wura"];

    return (
        <div className="dialogs">
            {users.map(
                (el) => (
                    <button>
                        <FaUser /> {el} <br/>
                        <AiFillMessage/> Her
                    </button>
                )
            )}
        </div>
    )
}

export default observer(BlockDialogs) 