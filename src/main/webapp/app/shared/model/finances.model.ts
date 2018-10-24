export interface IFINANCES {
    id?: number;
    url?: string;
    description?: string;
}

export class FINANCES implements IFINANCES {
    constructor(public id?: number, public url?: string, public description?: string) {}
}
