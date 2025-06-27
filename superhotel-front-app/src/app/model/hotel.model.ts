import { City } from "./city.model";
import { User } from "./user.model";

export class Hotel {
    id: number;
    name: string;
    address: string;
    phone: string;
    price: number;
    image: string;
    stars: number;
    availableRooms: number;
    city: City;
    recommandation?:string;
    totalRooms?:number;
    occupancyRate?:number;
    managers: User[];

    constructor(
        id: number,
        name: string,
        address: string,
        phone: string,
        price: number,
        image: string,
        stars: number,
        availableRooms: number,
        recommandation:string,
        city: City,
        managers: User[] = []
    ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.price = price;
        this.image = image;
        this.stars = stars;
        this.availableRooms = availableRooms;
        this.recommandation = recommandation;
        this.city = city;
        this.managers = managers;
    }
}
