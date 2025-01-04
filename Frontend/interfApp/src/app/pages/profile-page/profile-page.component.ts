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
        // console.log('User:', this.user);
        this.email = this.user.email;
      }
    );
  }

  getUserId(): string {
    this.jwtToken = this.cookieService.get('jwt');
    // console.log('JWT Token:', this.jwtToken);
    if (this.jwtToken) {
      const decodedToken: any = jwtDecode(this.jwtToken);
      this.user.id = decodedToken.id;
      // console.log('User ID:', this.user.id);
    }
    return this.user.id;
  }

  toggleEdit() {
    this.isEditable = !this.isEditable;
  }

  handlePasswordCheckResult(isPasswordCorrect: boolean) {
    console.log('Password check result:', isPasswordCorrect);
    if (isPasswordCorrect) {
      this.isEditable = !this.isEditable;
      // Handle the case when the password is correct
    } else {
      // Handle the case when the password is incorrect
    }
  }

  onSubmit() {
    console.log('Form data:', this.formUpdateProfile.value);
  }
}
