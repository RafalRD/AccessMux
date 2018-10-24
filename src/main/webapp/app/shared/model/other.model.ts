export interface IOTHER {
    id?: number;
    url?: string;
    description?: string;
}

export class OTHER implements IOTHER {
    constructor(public id?: number, public url?: string, public description?: string) {}
}
