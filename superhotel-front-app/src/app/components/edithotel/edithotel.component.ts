import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { City } from 'src/app/model/city.model';
import { Hotel } from 'src/app/model/hotel.model';
import { User } from 'src/app/model/user.model';
import { ApiService } from 'src/app/services/api.service';
import { AuthenticateService } from 'src/app/services/authenticate.service';

@Component({
  selector: 'app-edithotel',
  templateUrl: './edithotel.component.html',
  styleUrls: ['./edithotel.component.css']
})
export class EdithotelComponent {
  cities: City[] = [];
  managers: User[] = [];
  hotel: Hotel;
  error: string = "";
  isAdmin: boolean = false;
  myForm: FormGroup;
  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  status: boolean = false;

  managerId: number | null = null;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private apiService: ApiService,
    public authService: AuthenticateService,
    private formBuilder: FormBuilder
  ) {
    this.hotel = new Hotel(0, "", "", "", 0, "", 0, 0, new City(0, "", "", []), []);
    this.myForm = this.formBuilder.group({
      id: [this.hotel.id],
      name: [this.hotel.name, Validators.required],
      address: [this.hotel.address, Validators.required],
      phone: [this.hotel.phone, Validators.required],
      price: [this.hotel.price, Validators.required],
      stars: [this.hotel.stars, Validators.required],
      availableRooms: [this.hotel.availableRooms, Validators.required],
      city: [this.hotel.city.id, Validators.required],
      managerId: [null, Validators.required]
    });
  }

  ngOnInit() {
    const hotelId = Number(this.route.snapshot.paramMap.get('id'));
    this.isAdmin = this.authService.isAdmin();

    this.apiService.getCities().subscribe({
      next: (data) => {
        this.cities = data;
      },
      error: (err) => (this.error = err.message)
    });

    this.apiService.getManagers().subscribe({
      next: (data) => {
        this.managers = data;
      },
      error: (err) => (this.error = err.message)
    });

    if (hotelId > 0) {
      this.status = true;
      this.apiService.getHotelById(hotelId).subscribe({
        next: (data) => {
          this.hotel = data;
          const managerId = this.hotel.managers && this.hotel.managers.length > 0 ? this.hotel.managers[0].id : null;
          this.myForm.setValue({
            id: this.hotel.id,
            name: this.hotel.name,
            address: this.hotel.address,
            phone: this.hotel.phone,
            price: this.hotel.price,
            stars: this.hotel.stars,
            availableRooms: this.hotel.availableRooms,
            city: this.hotel.city.id,
            managerId: managerId
          });
          if (this.hotel.image) {
            this.imagePreview = 'http://localhost:8080/' + this.hotel.image;
          }
        },
        error: (err) => this.error = err.message
      });
    }
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  onAddHotel(form: FormGroup) {
    if (form.valid) {
      const cityId = form.value.city;
      const managerId = form.value.managerId;
      if (!cityId) {
        this.error = 'La ville est obligatoire';
        return;
      }
      if (!managerId) {
        this.error = 'Le manager est obligatoire';
        return;
      }

      const formData = new FormData();
      formData.append('name', form.value.name);
      formData.append('address', form.value.address);
      formData.append('phone', form.value.phone);
      formData.append('price', form.value.price);
      formData.append('stars', form.value.stars);
      formData.append('availableRooms', form.value.availableRooms);
      formData.append('city', cityId);
      formData.append('managerId', managerId);

      if (this.selectedFile) {
        formData.append('file', this.selectedFile);
      }

      if (this.status) {
        this.updateHotel(formData);
      } else {
        this.apiService.createHotel(formData).subscribe({
          next: () => this.router.navigateByUrl('hotels'),
          error: (err) => (this.error = err.message)
        });
      }
    } else {
      this.error = 'Erreur de saisie';
    }
  }

  updateHotel(formData: FormData) {
    this.apiService.updateHotel(this.myForm.value.id, formData).subscribe({
      next: () => {
        console.log('Hotel mise à jour avec succès');
        this.router.navigateByUrl('hotels');
      },
      error: (err) => this.error = err.message
    });
  }
}
