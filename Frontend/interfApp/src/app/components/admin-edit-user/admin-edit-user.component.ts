import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-edit-user',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-edit-user.component.html',
  styleUrls: ['./admin-edit-user.component.css']
})
export class AdminEditUserComponent implements OnInit {
  userId: string = '';  // ID of the user to edit
  user: any = {};       // User data to edit

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    // Retrieve the user ID from the URL
    this.userId = this.route.snapshot.paramMap.get('id')!;
    this.loadUserData();
  }

  // Load user data
  loadUserData() {
    this.userService.getUserById(this.userId).subscribe(
      (data) => {
        this.user = data;
        // If roles are present as a string, we convert them to an array
        if (data.roles) {
          this.user.roles = data.roles.split(','); // Convert string to array
        } else {
          this.user.roles = ['User']; // Initialize with 'User' if no roles
        }
        // Ensure the 'Admin' checkbox is checked if 'Admin' is part of the roles
        this.user.isAdmin = this.user.roles.includes('Admin');
      },
      (error) => {
        console.error('Error loading the user', error);
      }
    );
  }

  // Save the changes
  saveChanges() {
    // If the password is not modified, we remove it from the object
    if (!this.user.password || this.user.password.trim() === '') {
      delete this.user.password;
    }

    // Always include 'User' in the roles
    if (!this.user.roles.includes('User')) {
      this.user.roles.push('User');
    }

    // Add or remove 'Admin' based on the checkbox state
    if (this.user.isAdmin) {
      if (!this.user.roles.includes('Admin')) {
        this.user.roles.push('Admin');  // Add 'Admin' if the checkbox is checked
      }
    } else {
      // Remove 'Admin' if the checkbox is unchecked
      this.user.roles = this.user.roles.filter((role: string) => role !== 'Admin');
    }

    // Convert roles back to a string for sending to the server
    if (this.user.roles) {
      this.user.roles = this.user.roles.join(',');  // Convert array to string
    }

    // Update the user
    this.userService.updateUser(this.userId, this.user).subscribe(
      (response) => {
        console.log('User updated successfully');
        this.router.navigate(['/admin']); // Redirect to the admin user list
      },
      (error) => {
        console.error('Error updating the user', error);
      }
    );
  }
}
