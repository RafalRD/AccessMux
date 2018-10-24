export interface IMARKETING {
    id?: number;
    url?: string;
    description?: string;
}

export class MARKETING implements IMARKETING {
    constructor(public id?: number, public url?: string, public description?: string) {}
}
