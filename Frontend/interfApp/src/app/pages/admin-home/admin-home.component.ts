import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AdminUserComponent } from '../../components/admin-user/admin-user.component';

@Component({
  selector: 'app-admin-home',
  standalone: true,
  imports: [CommonModule, AdminUserComponent],
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent {
  activeView: string = 'users';
  isSidebarOpen: boolean = false;

  setActiveView(view: string) {
    this.activeView = view;
  }

  toggleSidebar() {
    this.isSidebarOpen = !this.isSidebarOpen;
    const sidebar = document.getElementById('sidebar');
    if (sidebar) {
      sidebar.classList.toggle('sidebar-active');
    }
  }
  
}
