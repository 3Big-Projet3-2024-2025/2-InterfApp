import { Component } from '@angular/core';
import {User} from "../../models/User";
import {CookieService} from "ngx-cookie-service";
import {jwtDecode} from "jwt-decode";
import {UserService} from "../../services/user.service";
import {
  ModalConfirmOldPasswordComponent
} from "../../components/modal-confirm-old-password/modal-confirm-old-password.component";

@Component({
  selector: 'app-profile-page',
  imports: [
    ModalConfirmOldPasswordComponent
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
  email: string = "email.test@gmail.com";

  jwtToken: string | null = null;
  isEditable = false;

  constructor(private cookieService: CookieService, private userService: UserService) { }
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
}
