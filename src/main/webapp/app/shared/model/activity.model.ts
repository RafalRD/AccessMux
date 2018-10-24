export interface IACTIVITY {
    id?: number;
    login?: string;
    role?: string;
    url?: string;
    time?: string;
}

export class ACTIVITY implements IACTIVITY {
    constructor(public id?: number, public login?: string, public role?: string, public url?: string, public time?: string) {}
}
