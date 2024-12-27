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
  styleUrl: './admin-edit-user.component.css'
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
        // If the backend returns roles as a string, we can convert them into an array
        if (data.roles) {
          this.user.roles = data.roles.split(','); // Convert string to array
        }
      },
      (error) => {
        console.error('Error loading the user', error);
      }
    );
  }

  // Save the changes
  saveChanges() {
    // If the password is not modified, remove it from the object
    if (!this.user.password || this.user.password.trim() === '') {
      delete this.user.password;
    }

    // If the user has changed roles, send them as a string
    if (this.user.roles) {
      this.user.roles = this.user.roles.join(',');  // Convert the array to a string
    }

    this.userService.updateUser(this.userId, this.user).subscribe(
      (response) => {
        console.log('User updated successfully');
        this.router.navigate(['/admin']); // Redirect to the user list
      },
      (error) => {
        console.error('Error updating the user', error);
      }
    );
  }
}
