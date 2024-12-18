import { Component } from '@angular/core';
import {User} from "../../models/User";
import {CookieService} from "ngx-cookie-service";
import {jwtDecode} from "jwt-decode";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-profile-page',
  imports: [],
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

  jwtToken: string | null = null;

  constructor(private cookieService: CookieService, private userService: UserService) { }
  ngOnInit(){
    this.userService.getUserById(this.getUserId()).subscribe(
      (data) => {
        this.user = data;
        // console.log('User:', this.user);
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
}
