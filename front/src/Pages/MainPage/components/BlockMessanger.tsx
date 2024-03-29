import React, {FC, useCallback, useContext, useEffect} from "react";
import { Context } from "../../..";
import '../css/BlockMessanger.css';
import {observer} from "mobx-react-lite";
import InfoDialogBlock from "./rightBlock/InfoDialogBlock";
import BlockWithMessages from "./rightBlock/BlockWithMessages";
import InputBlock from "./rightBlock/InputBlock";

const BlockMessanger: FC = () => {
    const {userStore} = useContext(Context);

    // useEffect(() => {
    //     userStore.dialogsStore.connect();
    //   }, []);

    return (
        <div className="rightBlock">
            {
                userStore.dialogsStore.isDialog ?
                <>
                <InfoDialogBlock />
                <BlockWithMessages />
                <InputBlock />
                </>
                :
                <></>
            }
        </div>
    )
}

export default observer(BlockMessanger) 