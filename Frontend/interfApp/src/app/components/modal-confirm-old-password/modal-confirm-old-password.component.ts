import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {User} from "../../models/User";
import {Router} from "@angular/router";

@Component({
  selector: 'app-modal-confirm-old-password',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  standalone: true,
  templateUrl: './modal-confirm-old-password.component.html',
  styleUrl: './modal-confirm-old-password.component.css'
})
export class ModalConfirmOldPasswordComponent {

  formPassword: FormGroup;

  @Input() user: User = {
    id: "",
    email: "",
    username: "",
    password: "",
    roles: [],
  };

  isPasswordCorrect: boolean = false;
  @Output() passwordCheckResult = new EventEmitter<boolean>();

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router) {
    this.formPassword = this.formBuilder.group({
      inputPassword: ['', [Validators.required]]
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

  onSubmit() {
    if (this.formPassword.valid) {
      this.sha512Hash(this.formPassword.value.inputPassword).then((hdata) => {
        this.user.password = hdata;
        this.userService.checkPassword(this.user).subscribe(
          (isPasswordCorrect) => {
            console.log("is my password correct? " + isPasswordCorrect);
            if (isPasswordCorrect) {
              // Handle successful password check
              console.log('Password is correct');
              this.isPasswordCorrect = true;
              this.passwordCheckResult.emit(this.isPasswordCorrect);
            } else {
              // Handle incorrect password
              console.log('Password is incorrect');
            }
          },
          (error) => {
            console.error(error);
          }
        );
      });
    } else {
      console.log("Veuillez remplir le formulaire");
    }
  }
}
