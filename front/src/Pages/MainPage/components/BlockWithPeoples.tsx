import React, {FC, useCallback, useContext} from "react";
import { Context } from "../../..";
import '../css/BlockWithPeoples.css';
import {observer} from "mobx-react-lite";
import SearchBlock from "./SearchBlock";
import BlockDialogs from "./BlockDialogs";

const BlockWithPeoples: FC = () => {
    const {store} = useContext(Context);


    return (
        <div className="leftBlock">
            <SearchBlock />
            <BlockDialogs />
        </div>
    )
}

export default observer(BlockWithPeoples) 