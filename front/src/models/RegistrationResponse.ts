class RegistrationResponse {
    status?: number = 200;
    message: string = "";

    constructor(message: string, status?: number | undefined) {
        this.status = status;
        this.message = message;
    }
    
}

export default RegistrationResponse