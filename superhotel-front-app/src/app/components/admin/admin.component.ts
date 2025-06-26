import { Component, OnInit } from '@angular/core';
import { Hotel } from 'src/app/model/hotel.model';
import { ApiService } from 'src/app/services/api.service';
import {Recommandation} from "../../model/recommandation.model";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  hotels: any[] = [];
  recommendations: Recommandation[] =[];

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.getHotels();
    this.getRecommendations()
  }

  getHotels() {
    this.apiService.getHotels().subscribe((data: any) => {
      this.hotels = data;
    });
  }

  getRecommendations(){
    this.apiService.getRecommandations().subscribe((data:any) =>{
      console.log(data);
      this.recommendations = data;
    })
  }

  deleteHotel(hotel : Hotel) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet hôtel ?')) {
      this.apiService.deleteHotel(hotel).subscribe(() => {
        this.getHotels();
      });
    }
  }
}
