import { Component} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  formRegister: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private registerService: UserService,
    private router: Router
  ) {
    this.formRegister = this.formBuilder.group({
      inputUsername: ['', Validators.required],
      inputemail: ['', [Validators.required, Validators.email]],
      inputPassword: ['', [Validators.required, Validators.minLength(6)]],
      inputConfirmPassword: ['', Validators.required]
    });
  }

  async sha512Hash(data: string): Promise<string> {
    const encoder = new TextEncoder();
    const dataBuffer = encoder.encode(data);
    const hashBuffer = await crypto.subtle.digest('SHA-512', dataBuffer);
    const hashArray = new Uint8Array(hashBuffer);
    const hashBase64 = btoa(String.fromCharCode(...hashArray));
    return hashBase64;
  }

  onSubmit(): void {
    this.errorMessage = null;  // Reset error message before validation
    if (this.formRegister.invalid) {
      this.errorMessage = 'Please check your information.';
      return;
    }
    
    if (this.formRegister.value.inputPassword !== this.formRegister.value.inputConfirmPassword) {
      this.errorMessage = 'Passwords do not match.';
      return;
    }

    const userData = {
      username: this.formRegister.value.inputUsername,
      email: this.formRegister.value.inputemail,
      password: '',
      roles: ''
    };

    this.sha512Hash(this.formRegister.value.inputPassword).then((hashedPassword) => {
      userData.password = hashedPassword;
      this.registerService.register(userData).subscribe(
        (response) => {
          console.log('User registered successfully!', response);
          this.router.navigate(['login']);
        },
        (error) => {
          this.errorMessage = 'An error occurred. Please try again.';
          console.error(error);
        }
      );
    });
  }
}
