import {FC, useContext} from "react";
import { Context } from "../..";
import './css/MainPage.css';
import {observer} from "mobx-react-lite";
import { checkIfStateModificationsAreAllowed } from "mobx/dist/internal";
import BlockWithPeoples from "./components/BlockWithPeoples";
import BlockMessanger from "./components/BlockMessanger";

const MainPage: FC = () => {
    const {store} = useContext(Context);


    return (
        <div className="mainPage">
            <BlockWithPeoples />
            <BlockMessanger />
        </div>
    )
}

export default observer(MainPage) 