import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from 'src/app/services/api.service';
import { AuthenticateService } from 'src/app/services/authenticate.service'; // Import du service d'auth
import { Hotel } from 'src/app/model/hotel.model';

@Component({
  selector: 'app-hotel',
  templateUrl: './hotel.component.html',
  styleUrls: ['./hotel.component.css']
})
export class HotelComponent implements OnInit {
  hotel: Hotel | undefined;
  error = null;
  isAdmin: boolean = false;
  isManager: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private apiService: ApiService,
    private authService: AuthenticateService // Injection du service d'authentification
  ) { }

  ngOnInit() {
    const hotelId = Number(this.route.snapshot.paramMap.get('id'));
    this.getHotelDetails(hotelId);
    this.isAdmin = this.authService.isAdmin();
    this.isManager = this.authService.isManager();
  }

  getHotelDetails(id: number) {
    this.apiService.getHotelById(id).subscribe({
      next: (data) => (this.hotel = data),
      error: (err) => (this.error = err.message),
    });
  }

  onDeleteHotel(hotel: Hotel) {
    if (confirm("Vous êtes sur de vouloir supprimer cet Hotel ?")) {
      this.apiService.deleteHotel(hotel).subscribe({
        next: (data) => console.log(data),
        error: (err) => this.error = err.message,
        complete: () => this.router.navigateByUrl('/hotels'),
      })
    }
  }

  editHotel() {
    this.router.navigate(['/edithotel', this.hotel!.id]);
  }
}
