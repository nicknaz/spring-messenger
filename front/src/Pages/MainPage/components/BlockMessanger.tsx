import React, {FC, useCallback, useContext} from "react";
import { Context } from "../../..";
import '../css/BlockMessanger.css';
import {observer} from "mobx-react-lite";

const BlockMessanger: FC = () => {
    const {store} = useContext(Context);


    return (
        <div className="rightBlock">

        </div>
    )
}

export default observer(BlockMessanger) 