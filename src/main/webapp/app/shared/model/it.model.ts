export interface IIT {
    id?: number;
    url?: string;
    description?: string;
}

export class IT implements IIT {
    constructor(public id?: number, public url?: string, public description?: string) {}
}
