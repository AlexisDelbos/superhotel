import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EdithotelComponent } from './edithotel.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('EdithotelComponent', () => {
  let component: EdithotelComponent;
  let fixture: ComponentFixture<EdithotelComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EdithotelComponent ],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(EdithotelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
