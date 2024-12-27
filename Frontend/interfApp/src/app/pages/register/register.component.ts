import { Component} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  formRegister: FormGroup;

  errorMessage: string | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private registerService: UserService,
    private router: Router // Correction : Angular Router
  ) {
    this.formRegister = this.formBuilder.group({
      inputUsername: ['', Validators.required],
      inputemail: ['', [Validators.required, Validators.email]],
      inputPassword: ['', [Validators.required, Validators.minLength(6)]],
      inputConfirmPassword: ['', Validators.required]
    });
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
    if (this.formRegister.valid && this.formRegister.value.inputPassword === this.formRegister.value.inputConfirmPassword) {
      const userData = {
        username: this.formRegister.value.inputUsername,
        email: this.formRegister.value.inputemail,
        password: "",
        roles:"",
      };

      this.sha512Hash(this.formRegister.value.inputPassword).then((hdata) => {
        userData.password = hdata
        this.registerService.register(userData).subscribe(
          (response) => {
            //rediriger l'utilisateur ou afficher un message de succès
            console.log('Utilisateur inscrit avec succès!', response);
            this.router.navigate(['login']);
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
}
