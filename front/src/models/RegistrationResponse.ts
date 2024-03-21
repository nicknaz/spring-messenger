class RegistrationResponse {
    status?: number = 200;
    messege: string = "";

    constructor(messege: string, status?: number | undefined) {
        this.status = status;
        this.messege = messege;
    }
    
}

export default RegistrationResponse