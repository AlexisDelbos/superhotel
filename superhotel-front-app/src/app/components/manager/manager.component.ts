import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/services/api.service';  
import { User } from 'src/app/model/user.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-manager',
  templateUrl: './manager.component.html',
  styleUrls: ['./manager.component.css'],
})
export class ManagerComponent implements OnInit {
  myForm: FormGroup;
  error: string = "";
  errorMessage: any;

  constructor(
    private apiService: ApiService, 
    private router: Router, 
    private formBuilder: FormBuilder
  ) {
    this.myForm = this.formBuilder.group({
      username: ["", Validators.required],
      password: ["", Validators.required],
    });
  }

  ngOnInit(): void {}

  onAddManager() {
    console.log(this.myForm.value);
    if (this.myForm.invalid) {
      this.error = "Veuillez remplir tous les champs.";
      return;
    }

    const newManager: User = {
      id: null,
      username: this.myForm.value.username,
      password: this.myForm.value.password,
      role: { id: 2, role: "MANAGER" }, 
      hotels: []
    };
    console.log(newManager, typeof newManager);

    this.apiService.createManager(newManager).subscribe({
      next: () =>

        this.router.navigateByUrl('admin'),
      error: (err) => (this.error = err.error?.message || "Erreur lors de la création du manager")
    });
  }
}
