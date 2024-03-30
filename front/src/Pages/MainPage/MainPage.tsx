import {FC, useContext} from "react";
import { Context } from "../..";
import './css/MainPage.css';
import {observer} from "mobx-react-lite";
import { checkIfStateModificationsAreAllowed } from "mobx/dist/internal";
import BlockWithPeoples from "./components/leftBlock/BlockWithPeoples";
import BlockMessanger from "./components/BlockMessanger";

const MainPage: FC = () => {
    const {userStore} = useContext(Context);


    return (
        <div className="mainPage">
            {
                window.matchMedia('(orientation: portrait)').matches 
                ?
                    !userStore.dialogsStore.isVisibleDialog 
                    ?
                    <BlockWithPeoples /> 
                    :
                    <BlockMessanger />
                :
                <>
                    <BlockWithPeoples />
                    <BlockMessanger />
                </>
            }
            
        </div>
    )
}

export default observer(MainPage) 