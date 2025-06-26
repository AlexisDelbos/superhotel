import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticateService } from './services/authenticate.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'trainings-front-app';
  showNav: boolean = true;

  constructor(private router: Router, public authService: AuthenticateService) {
    this.router.events.subscribe(() => {
      this.showNav = this.router.url !== '/login';
    });
  }
  onLogin() {
  }
}


