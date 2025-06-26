import { Hotel } from "./hotel.model";

export class City {
    id: number;
    name: string;
    country: string;
    hotels: Hotel[];

    constructor(id: number, name: string, country: string, hotels: Hotel[]) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.hotels = hotels;
    }
}