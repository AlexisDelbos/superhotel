import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CityComponent } from './city.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

describe('CityComponent', () => {
  let component: CityComponent;
  let fixture: ComponentFixture<CityComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CityComponent],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule,
        FormsModule
      ],
    })
      .compileComponents();

    fixture = TestBed.createComponent(CityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

});
