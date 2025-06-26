import { TokenService } from './../../services/token.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ICredential } from 'src/app/model/credential';
import { User } from 'src/app/model/user.model';
import { AuthenticateService } from 'src/app/services/authenticate.service';

@Component({
  selector: 'app-loginout',
  templateUrl: './loginout.component.html',
  styleUrls: ['./loginout.component.css']
})

/**
 * Composant de gestion de l'authentification d'un utilisateur
 */
export class LoginoutComponent implements OnInit {
  myForm: ICredential = {
    username: "",
    password: ""
  };
  user: User | undefined;
  error: string | undefined;
  connected: boolean = false;

  constructor(private formBuilder: FormBuilder, public authService: AuthenticateService, private router: Router, private tokenService: TokenService) {
    this.user = authService.getUser();
    this.connected = authService.isConnected();
  }

  ngOnInit(): void {
    this.user = new User(0, "", "", { id: 0, role: "" });
  }

  onLogin(): void {
    console.log("Connexion " + this.myForm.username + " " + this.myForm.password);

    this.authService.login(this.myForm).subscribe(
      data => {
        if (!data["access-token"]) {
          console.error("Aucun token reçu !");
          this.error = "Erreur de connexion. Aucun token reçu.";
          return;
        }
        this.tokenService.saveToken(data["access-token"]);
        let userInfo = this.tokenService.getPayload();
        console.log("Utilisateur récupéré après decrypt :", userInfo);

        if (userInfo.username && userInfo.role) {
          this.authService.setUser(userInfo);
          this.user = new User(0, userInfo.username, "", { id: 0, role: userInfo.role });
          this.connected = true;
          this.router.navigate(['hotels']);
        } else {
          console.log("Erreur : Impossible de récupérer les infos utilisateur.");
          this.error = "Erreur de connexion.";
        }
      },
      err => {
        console.log("Erreur Login :", err);
        this.error = "Identifiants incorrects ou problème serveur";
      }
    );
  }



  onAddUser() {

  }

  goToHome(): void {
    this.router.navigateByUrl('hotels');
  }


  /**
   * Méthode de déconnexion d'un utilisateur
   */
  disconnect() {
    this.authService.disconnected();
    this.connected = false;
    this.router.navigateByUrl('hotels');
  }
}
