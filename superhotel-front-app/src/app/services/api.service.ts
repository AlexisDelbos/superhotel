import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Hotel } from '../model/hotel.model';
import { ICredential } from '../model/credential';
import { Observable } from 'rxjs';
import { IToken } from '../model/token';
import { City } from '../model/city.model';
import { User } from '../model/user.model';
import {Recommandation} from "../model/recommandation.model";

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  constructor(private httpClient: HttpClient) {}

  public getHotels() {
    return this.httpClient.get<Hotel[]>(environment.host + '/hotels');
  }

  public getHotelById(id: number) {
    return this.httpClient.get<Hotel>(environment.host + '/hotels/' + id);
  }

  public login(credentials: ICredential): Observable<IToken> {
    console.log(
      'Login service ' + credentials.username + ' ' + credentials.password
    );
    return this.httpClient.post<IToken>(
      `${environment.host}/login`,
      credentials
    );
  }

  public createHotel(formData: FormData): Observable<any> {
    return this.httpClient.post<Hotel>(environment.host + '/hotels', formData);
  }

  public updateHotel(id: number, formData: FormData): Observable<any> {
    return this.httpClient.put(`${environment.host}/hotels/${id}`, formData);
  }

  public deleteHotel(hotel: Hotel) {
    return this.httpClient.delete(environment.host + '/hotels/' + hotel.id);
  }

  public getCities() {
    return this.httpClient.get<City[]>(environment.host + '/cities');
  }

  addManagerToHotel(hotelId: number, managerId: number): Observable<Hotel> {
    return this.httpClient.post<Hotel>(
      `http://localhost:8080/api/hotels/${hotelId}/managers/${managerId}`,
      {}
    );
  }

  public getRecommandations(){
    return this.httpClient.get<any>(environment.host + '/getrecommandation')
  }

  public getManagers() {
    return this.httpClient.get<User[]>(environment.host + '/managers');
  }

  public createManager(user: User): Observable<User> {
    console.log('Manager service', user);
    return this.httpClient.post<User>(`${environment.host}/managers`, user);
  }

  public createCity(city: City): Observable<City> {
    console.log('City service', city);
    return this.httpClient.post<City>(`${environment.host}/cities`, city);
  }

}
