import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router'; // Correctly imported from Angular

@Component({
  selector: 'app-admin-user',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-user.component.html',
  styleUrls: ['./admin-user.component.css'] // Corrected "styleUrl" to "styleUrls"
})
export class AdminUserComponent implements OnInit {
  users: any[] = []; // Full list of users
  pagedUsers: any[] = []; // Users displayed in the pagination
  currentPage: number = 1;
  pageSize: number = 8;
  totalPages: number = 0;

  constructor(private userService: UserService, private router: Router) {} // Injecting Router

  ngOnInit() {
    this.loadUsers();
  }

  // Load all users
  loadUsers() {
    this.userService.getAllUsers().subscribe(
      (data) => {
        console.log('Users loaded:', data); // Check the structure of the data here
        this.users = data.map(user => ({
          ...user,
          roles: user.roles ? user.roles.split(',') : [] // Convert the string to an array
        }));
        this.totalPages = Math.ceil(this.users.length / this.pageSize);
        this.updatePagedUsers();
      },
      (error) => {
        console.error('Error loading users', error);
      }
    );
  }

  // Update the users displayed for the current page
  updatePagedUsers() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.pagedUsers = this.users.slice(startIndex, endIndex);
  }

  // Go to a specific page
  goToPage(page: number) {
    if (page < 1 || page > this.totalPages) return;
    this.currentPage = page;
    this.updatePagedUsers();
  }

  // Delete a user
  deleteUser(id: string) {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(id).subscribe(
        () => {
          this.users = this.users.filter((user) => user.id !== id);
          this.updatePagedUsers();
        },
        (error) => {
          console.error('Error deleting user', error);
        }
      );
    }
  }

  // Edit a user (redirects to the edit page)
  editUser(id: string) {
    this.router.navigate([`/admin/users/edit/${id}`]);
  }

  // Total number of pages for pagination
  get totalPagesArray(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i + 1);
  }
}
