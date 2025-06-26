import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginoutComponent } from './loginout.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';


describe('LoginoutComponent', () => {
  let component: LoginoutComponent;
  let fixture: ComponentFixture<LoginoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginoutComponent],
      imports: [
        RouterTestingModule,
        HttpClientTestingModule,
        FormsModule
      ],
    })
      .compileComponents();

    fixture = TestBed.createComponent(LoginoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
