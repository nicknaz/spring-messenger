import React from "react";
import LoginForm from "./LoginForm";
import "../../css/WelcomePage.css";
import RegistrationForm from "./RegistrationForm";
import axios from "axios";
import Header from "../../components/Header";

interface IProps {
}

interface IState {
    isLoginForm?: boolean;
}

class WelcomePage extends React.Component<IProps, IState> {
    constructor (props: any) {
        super(props);

        this.state = {
            isLoginForm: true,
        }

        this.changeForm = this.changeForm.bind(this);
    }

    render () {
        return (
            <div className="auth">
                <Header />
                <h2>Welcome!</h2>
                {this.state.isLoginForm ? <LoginForm /> : <RegistrationForm/>}
                <form className="loginForm">
                    <button type="button" id="otherFormButton" onClick={() => {this.setState({isLoginForm: !this.state.isLoginForm})}}>{this.state.isLoginForm ? <p>Registration</p> : <p>LogIn</p>}</button>
                </form>
                
            </div>  
        )
    }

    changeForm(state: boolean) {
        this.setState({isLoginForm: state});
    }

}

export default WelcomePage