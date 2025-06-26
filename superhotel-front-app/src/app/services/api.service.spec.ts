import { TestBed } from '@angular/core/testing';

import { ApiService } from './api.service';
import { HttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { City } from '../model/city.model';

describe('ApiService', () => {
  let service: ApiService;
  let httpClientSpy : {get : jasmine.Spy}

  beforeEach(() => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    TestBed.configureTestingModule({
      providers : [ ApiService,
        {provide : HttpClient, useValue : httpClientSpy}
      ]
    });
    service = TestBed.inject(ApiService);
  });

  it('should return all values in an array', (done: DoneFn) => {
    const expectedResults = [
      {id: 1, name : "Hotel Eiffel", price : 490.00}
    ];

    httpClientSpy.get.and.returnValue(of(expectedResults));

    service.getHotels().subscribe(hotels => {
      expect(hotels.length).toBe(1);
      expect(hotels.includes({
        id: 1, name: "Hotel Eiffel", price: 490.00,
        address: '',
        phone: '',
        image: '',
        stars: 0,
        availableRooms: 0,
        city: new City(1, 'Paris', 'France', []), 
        managers: []
      }));
      done();
    })
  })

  it('should return a single hotel by ID', (done: DoneFn) => {
    const hotelId = 1;
    const expectedHotel = {
      id: hotelId,
      name: 'Hotel Eiffel',
      price: 490.00,
      address: '',
      phone: '',
      image: '',
      stars: 0,
      availableRooms: 0,
      city: new City(1, 'Paris', 'France', []),
      managers: []
    };
  
    httpClientSpy.get.and.returnValue(of(expectedHotel));
  
    service.getHotelById(hotelId).subscribe(hotel => {
      expect(hotel).toEqual(expectedHotel);
      done();
    });
  });
  


});
