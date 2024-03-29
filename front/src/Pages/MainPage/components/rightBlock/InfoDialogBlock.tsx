import React, {FC, useCallback, useContext} from "react";
import { Context } from "../../../..";
import DialogsStore from "../../../../store/DialogsStore";
import '../../css/BlockMessanger.css';
import {observer} from "mobx-react-lite";

import { GiHamburgerMenu } from "react-icons/gi";
import { FaSearch, FaUser} from "react-icons/fa";
import { CiLogout } from "react-icons/ci";

const InfoDialogBlock: FC = () => {
    const {userStore} = useContext(Context);
    const dialogsStore : DialogsStore = userStore.dialogsStore;


    return (
        <div className="info">
            <FaUser />  {dialogsStore.currentRecepient.username}
        </div>
    )
}

export default observer(InfoDialogBlock)