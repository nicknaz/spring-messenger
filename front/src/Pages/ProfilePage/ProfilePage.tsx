import React, {FC, useContext, useState} from "react";
import { observe } from "mobx";
import { observer } from "mobx-react-lite"
import ProfileUser from "./ProfileUser";

class ProfilePage extends React.Component {
    constructor (props: any) {
        super(props)
        
        //user = this.props.user;
    }

    render () {
        return (
            <div id="profile">
                <ProfileUser />
            </div>
        )
    }

}

export default ProfilePage