import { ITokenUser } from './../model/user.model';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { JwtPayload } from 'jwt-decode';
@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor(private router: Router) { }

  saveToken(token: string) {
    console.log("Token sauvegardé : ", token);
    localStorage.setItem('token', token);
  }


  isLogged(): boolean {
    const token = localStorage.getItem('token');
    return !!token;
  }

  clearToken() {
    localStorage.removeItem('token');
    this.router.navigate(['/']);
  }

  clearTokenExpired(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getPayload(): any {
    let userConnected: ITokenUser = {
      id: 0,
      username: '',
      role: ''
    };

    let token = localStorage.getItem('token');

    if (token) {
      console.log("Token avant décodage:", token);

      try {
        if (token.split('.').length !== 3) {
          throw new Error("Token mal formé");
        }

        const decoded: any = jwtDecode(token);
        console.log("Token décodé:", decoded);

        userConnected.username = decoded.sub;
        userConnected.role = decoded.scope.includes("ADMIN") ? "ADMIN" : decoded.scope.includes("MANAGER") ? "MANAGER" : "USER";

        if (decoded.scope.includes("USER") && decoded.scope.includes("ADMIN")) {
          userConnected.role = "ADMIN,USER";
        } else if (decoded.scope.includes("USER") && decoded.scope.includes("MANAGER")) {
          userConnected.role = "MANAGER,USER";
        }

      } catch (error) {
        console.error("Erreur lors du décodage du token :", error);
        this.clearToken();
      }
    }

    return userConnected;
  }

}
