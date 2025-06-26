import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/services/api.service';
import { City } from 'src/app/model/city.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-city',
  templateUrl: './city.component.html',
  styleUrls: ['./city.component.css']
})
export class CityComponent implements OnInit {

  city: City = new City(0, '', '', []);
  myFormCity : FormGroup;
  error: string = '';

  constructor(private apiService: ApiService, private formBuilder : FormBuilder, private router : Router) {

    this.myFormCity = this.formBuilder.group({
      name: [this.city.name],
      country: [this.city.country]
    });

   }

  ngOnInit() { }

  onAddCity() {
        if (this.myFormCity.invalid) {
          this.error = "Veuillez remplir tous les champs.";
          return;
        }
    
        const newCity: City = {
          id: 0,
          name: this.myFormCity.value.name,
          country: this.myFormCity.value.country,
          hotels: []
        };

    this.apiService.createCity(newCity).subscribe({
      
      next: () =>
        console.log('City OnaddCity', this.city),
      error: (err) => console.log('Error:', err.error.message)
    });
  }
}