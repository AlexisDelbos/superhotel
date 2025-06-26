import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HotelsComponent } from './hotels.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';


describe('HotelsComponent', () => {
  let component: HotelsComponent;
  let fixture: ComponentFixture<HotelsComponent>;
  let hotelServiceSpy:{getAllHotels: jasmine.Spy};
  let httpClientSpy: { get: jasmine.Spy };

  beforeEach(async () => {
    httpClientSpy = jasmine.createSpyObj('HttpClient', ['get']);
    hotelServiceSpy = jasmine.createSpyObj('HotelService', ['getAllHotels']);
    await TestBed.configureTestingModule({
      declarations: [HotelsComponent],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule
      ],
    })
      .compileComponents();

    fixture = TestBed.createComponent(HotelsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    hotelServiceSpy.getAllHotels.and.returnValue(of([
      { id: 1, name: 'Hotel Eiffel', price: 490.00 },
      { id: 2, name: 'Hotel Velodrome', price: 50.00 },
      { id: 3, name: 'Hotel Georges', price: 650.00 }
    ]));
  });

});
