export interface ISALE {
    id?: number;
    url?: string;
    description?: string;
}

export class SALE implements ISALE {
    constructor(public id?: number, public url?: string, public description?: string) {}
}
