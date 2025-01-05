import { Component } from '@angular/core';
import {User} from "../../models/User";
import {CookieService} from "ngx-cookie-service";
import {jwtDecode} from "jwt-decode";
import {UserService} from "../../services/user.service";
import {
  ModalConfirmOldPasswordComponent
} from "../../components/modal-confirm-old-password/modal-confirm-old-password.component";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";

@Component({
  selector: 'app-profile-page',
  imports: [
    ModalConfirmOldPasswordComponent,
    FormsModule,
    ReactiveFormsModule
  ],
  standalone: true,
  templateUrl: './profile-page.component.html',
  styleUrl: './profile-page.component.css'
})
export class ProfilePageComponent {
  user: User = {
    id: "",
    email: "",
    username: "",
    password: "",
    roles: [],
  }
  email: string = "";

  jwtToken: string | null = null;
  isEditable = false;

  formUpdateProfile: FormGroup;

  constructor(private formBuilder: FormBuilder, private cookieService: CookieService, private userService: UserService) {
    this.formUpdateProfile = this.formBuilder.group({
      inputEmail: [],
      inputUsername: [],
      inputPassword1: [],
      inputPassword2: []
    });
  }
  ngOnInit(){
    this.userService.getUserById(this.getUserId()).subscribe(
      (data) => {
        this.user = data;
        console.log("User data on init:", this.user);
        this.formUpdateProfile.value.inputEmail = this.user.email;
        this.formUpdateProfile.value.inputUsername = this.user.username;
        console.log("Form data on init:", this.formUpdateProfile.value);
      }
    );
  }

  getUserId(): string {
    this.jwtToken = this.cookieService.get('jwt');
    if (this.jwtToken) {
      const decodedToken: any = jwtDecode(this.jwtToken);
      this.user.id = decodedToken.id;
    }
    return this.user.id;
  }

  toggleEdit() {
    // this.isEditable = !this.isEditable;
    if (this.formUpdateProfile.valid) {
      if(this.formUpdateProfile.value.inputEmail != null){
        this.user.email = this.formUpdateProfile.value.inputEmail;
      }
      if(this.formUpdateProfile.value.inputUsername != null){
        this.user.username = this.formUpdateProfile.value.inputUsername;
      }
      if(this.formUpdateProfile.value.inputPassword1 != null && this.formUpdateProfile.value.inputPassword2 != null){
        if (this.formUpdateProfile.value.inputPassword1 === this.formUpdateProfile.value.inputPassword2) {
          // this.user.password = this.formUpdateProfile.value.inputPassword1;
          this.sha512Hash(this.formUpdateProfile.value.inputPassword1).then((hdata) => {
            this.user.password = hdata;
            console.log('hdata:', hdata);
            this.userService.updateUser(this.user.id,this.user).subscribe(() => {
              window.location.reload()});
          });
        }

      }
      else {
        this.userService.updateUser(this.user.id,this.user).subscribe(() => {
          window.location.reload()});
        console.log('Form data:', this.formUpdateProfile.value);
        console.log('User data final to send:', this.user);
      }


      // window.location.reload();
        }
  }

  handlePasswordCheckResult(event :{isPasswordCorrect: boolean, hashedPassword: string}) {
    if (event.isPasswordCorrect) {
      this.user.password = event.hashedPassword;
      this.isEditable = !this.isEditable;
      console.log('User data:', this.user);
    } else {
      // Handle the case when the password is incorrect
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

}
