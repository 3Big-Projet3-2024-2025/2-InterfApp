import {Component, Input} from '@angular/core';
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
  password?: string;
  @Input() user: User = {
    id: "",
    email: "",
    username: "",
    password: "",
    roles: [],
  };
  // @Input() id?: string;

  constructor(private formBuilder: FormBuilder, private loginService: UserService, private router: Router) {
    this.formPassword = this.formBuilder.group({
      inputPassword: ['', [Validators.required]]
    });
  }

  ngOnInit() {
    this.loginService.checkPassword(this.user);
    console.log("my email in child component: " + this.user?.email);
    console.log("my id in my child component: " + this.user?.id);
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
      const userData = {
        email: this.user?.email,
        password: this.formPassword.value.inputPassword
      };

      this.sha512Hash(this.formPassword.value.inputPassword).then((hdata) => {
        userData.password = hdata
        console.log("my user to login: " + userData + " " + userData.email + " " + userData.password);
        this.loginService.login(userData).subscribe(
          (response) => {
            console.log(response);
            // this.loginService.saveJwt(response.token, userData.rememberMe);
            this.router.navigate(['forms']);
            // Vous pouvez rediriger l'utilisateur ou afficher un message de succès
            console.log('Utilisateur est connecté avec succès!', response);
          },
          (error) => {
            console.log("not logged in");
            // this.errorMessage = 'Une erreur est survenue, veuillez réessayer.';
            console.error(error);
          }
        );
      });
    } else {
      // this.errorMessage = 'Veuillez vérifier vos informations.';
    }
  }
}
