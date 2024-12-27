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
  userId: string = '';  // ID de l'utilisateur à éditer
  user: any = {};       // Informations de l'utilisateur à éditer

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    // Récupérer l'ID de l'utilisateur depuis l'URL
    this.userId = this.route.snapshot.paramMap.get('id')!;
    this.loadUserData();
  }

  // Charger les données de l'utilisateur
  loadUserData() {
    this.userService.getUserById(this.userId).subscribe(
      (data) => {
        this.user = data;
        // Si le backend retourne les rôles sous forme de chaîne, on peut les transformer en tableau
        if (data.roles) {
          this.user.roles = data.roles.split(','); // transformer la chaîne en tableau
        }
      },
      (error) => {
        console.error('Erreur lors du chargement de l\'utilisateur', error);
      }
    );
  }

  // Sauvegarder les modifications
  saveChanges() {
    // Si le mot de passe n'est pas modifié, on le retire de l'objet
    if (!this.user.password || this.user.password.trim() === '') {
      delete this.user.password;
    }

    // Si l'utilisateur a changé des rôles, on les envoie en tant que chaîne
    if (this.user.roles) {
      this.user.roles = this.user.roles.join(',');  // convertir le tableau en chaîne
    }

    this.userService.updateUser(this.userId, this.user).subscribe(
      (response) => {
        console.log('Utilisateur mis à jour avec succès');
        this.router.navigate(['/admin']); // Rediriger vers la liste des utilisateurs
      },
      (error) => {
        console.error('Erreur lors de la mise à jour de l\'utilisateur', error);
      }
    );
  }
}
