import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../service/user.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  formLogin: FormGroup;

  errorMessage: string | null = null;

  constructor( private formBuilder: FormBuilder,private loginService: UserService, private router : Router ) {
    this.formLogin = this.formBuilder.group({
      inputemail: ['', [Validators.required]],
      inputPassword: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
     if(this.loginService.isAuthenticated()){
      this.router.navigate(['forms']);
     }
  }

  async sha512Hash(data: string): Promise<string> {
    // Convert the input string to an ArrayBuffer
    const encoder = new TextEncoder();
    const dataBuffer = encoder.encode(data);

    // Perform the SHA-512 hashing operation
    const hashBuffer = await crypto.subtle.digest('SHA-512', dataBuffer);

    // Convert the hash ArrayBuffer to a Base64 string
    const hashArray = new Uint8Array(hashBuffer);
    const hashBase64 = btoa(String.fromCharCode(...hashArray));

    return hashBase64;
  }

  onSubmit(): void {
    if (this.formLogin.valid) {
      const userData = {
        email: this.formLogin.value.inputemail,
        password: this.formLogin.value.inputPassword,
      };

      this.sha512Hash(this.formLogin.value.inputPassword).then((hdata) => {
        userData.password = hdata
        this.loginService.login(userData).subscribe(
          (response) => {
            console.log(response);
            this.loginService.saveJwt(response.token);
            this.router.navigate(['forms']);
            // Vous pouvez rediriger l'utilisateur ou afficher un message de succès
            console.log('Utilisateur est connecté avec succès!', response);
          },
          (error) => {
            this.errorMessage = 'Une erreur est survenue, veuillez réessayer.';
            console.error(error);
          }
        );
      });  
    } else {
      this.errorMessage = 'Veuillez vérifier vos informations.';
    }
  }

  navigateRegister(): void {
    this.router.navigate([`/register`]);
  }
}

