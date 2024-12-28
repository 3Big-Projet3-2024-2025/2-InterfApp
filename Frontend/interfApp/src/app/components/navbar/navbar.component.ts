import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {Router, RouterLink} from '@angular/router';
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {

  constructor( private router: Router,private userService : UserService) {}

  get isAuthenticated() : boolean {
    return this.userService.isAuthenticated();
  }

  get isAdmin(): boolean {
    return this.userService.hasRole('Admin');
  }

  onAuthButtonClick(): void {
    if (this.isAuthenticated) {
      this.userService.logout();
    }
    this.router.navigate(['/login']);
  }

  navigate(route: string): void {
    this.router.navigate([`/${route}`]);
  }
}
