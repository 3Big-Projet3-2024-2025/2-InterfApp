import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  isAuthenticated: boolean = false;

  constructor( private router: Router,private userService : UserService) {}

  ngOnInit(): void {
    this.isAuthenticated = this.userService.isAuthenticated();
  }

  onAuthButtonClick(): void {
    if (this.isAuthenticated) {
      this.userService.logout();
      this.isAuthenticated = false;
      this.router.navigate(['/login']);
    } else {
      this.router.navigate(['/login']);
    }
  }

  navigate(route: string): void {
    this.router.navigate([`/${route}`]);
  }
}
