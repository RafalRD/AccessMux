export interface IHR {
    id?: number;
    url?: string;
    description?: string;
}

export class HR implements IHR {
    constructor(public id?: number, public url?: string, public description?: string) {}
}
