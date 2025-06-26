import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { City } from 'src/app/model/city.model';
import { Hotel } from 'src/app/model/hotel.model';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-hotels',
  templateUrl: './hotels.component.html',
  styleUrls: ['./hotels.component.css']
})
export class HotelsComponent implements OnInit {
  listHotels: Hotel[] = [];
  listCities: City[] = [];
  listFilteredHotels: Hotel[] | undefined;
  error = null;
  selectedCityId: string = ''; 
  hoverCard: number | null = null;
  searchQuery: string = ''; 


  constructor(private router: Router, private apiService: ApiService) { }

  ngOnInit(): void {
    this.getAllHotels();
    this.getAllCities();
  }

  goToDetail(id: number) {
    this.router.navigate(['/hotel', id]);
  }


  getAllHotels() {
    this.apiService.getHotels().subscribe({
      next: (data) => {
        this.listHotels = data;
        this.listFilteredHotels = data;
      },
      error: (err) => (this.error = err.message),
      complete: () => (this.error = null),
    });
  }

  getAllCities() {
    this.apiService.getCities().subscribe({
      next: (data) => {
        this.listCities = data;
      },
      error: (err) => (this.error = err.message),
      complete: () => (this.error = null),
    });
  }

  filterByCity(): void {
    if (this.selectedCityId) {
      const cityId = parseInt(this.selectedCityId, 10);
      this.listFilteredHotels = this.listHotels.filter(
        (hotel) => hotel.city && hotel.city.id === cityId
      );
    } else {
      this.listFilteredHotels = this.listHotels; 
    }
  }

  searchHotels(): void {
    if (this.searchQuery.trim()) {
      this.listFilteredHotels = this.listHotels.filter((hotel) =>
        hotel.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        hotel.address.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        hotel.city.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    } else {
      this.listFilteredHotels = this.listHotels;
    }
  }

  resetFilter(): void {
    this.listFilteredHotels = this.listHotels;
  }

}
