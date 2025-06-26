import { Hotel } from "./hotel.model";

export class User {
    
    constructor(
        public id: number | null,
        public username: string,
        public password: string,
        public role: { id: number; role: string },
        public hotels: Hotel[] = [] 
    ) { }
}


export interface ITokenUser {
    id: number;
    username: string;
    role: string;
}
