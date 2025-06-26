import { Injectable } from '@angular/core';
import { ITokenUser, User } from '../model/user.model';
import { ApiService } from './api.service';
import { ICredential } from '../model/credential';

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {
  userConnected: User = new User(0, "", "", { id: 0, role: "" });

  constructor(private apiService: ApiService) { }
  
  getUser(): User {
    let user = localStorage.getItem('user');
    if (user) {
      try {
        this.userConnected = JSON.parse(user);
      } catch (e) {
        console.error("Erreur lors du parsing du JSON :", e);
        localStorage.removeItem('user');
      }
    }
    return this.userConnected;
  }

  login(credentials: ICredential) {
    return this.apiService.login(credentials);
  }

  isConnected() {
    return localStorage.getItem('user') != null;
  }

  disconnected() {
    localStorage.removeItem('user');
    this.userConnected = new User(0, "", "", { id: 0, role: "" });
  }

  isAdmin(): boolean {
    let user = this.getUser();
    return user && user.role && user.role.role.includes('ADMIN');
  }

  isUser() {
    let user = this.getUser();
    return user && user.role && user.role.role.includes('USER');
  }

  isManager() {
    let user = this.getUser();
    return user && user.role && user.role.role.includes('MANAGER');
  }
  setUser(user: ITokenUser): void {
    if (user && user.username && user.role) {
      this.userConnected = new User(0, user.username, "", { id: 0, role: user.role });

      localStorage.setItem('user', JSON.stringify(this.userConnected));
    } else {
      console.error("⚠️ Données utilisateur invalides:", user);
    }
  }
}
