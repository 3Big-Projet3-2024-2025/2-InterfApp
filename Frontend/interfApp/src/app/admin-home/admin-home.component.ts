import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-home.component.html',
  styleUrl: './admin-home.component.css'
})
export class AdminHomeComponent {
  constructor(private router: Router) {}

  // Méthode pour naviguer vers la gestion des utilisateurs
  goToUsers() {
    this.router.navigate(['/admin/users']);
  }

  // Méthode pour naviguer vers la gestion des groupes
  goToGroups() {
    this.router.navigate(['/admin/groups']);
  }
}
