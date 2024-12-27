import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router'; // Correctement importé depuis Angular

@Component({
  selector: 'app-admin-user',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-user.component.html',
  styleUrls: ['./admin-user.component.css'] // Corrigé "styleUrl" en "styleUrls"
})
export class AdminUserComponent implements OnInit {
  users: any[] = []; // Liste complète des utilisateurs
  pagedUsers: any[] = []; // Utilisateurs affichés dans la pagination
  currentPage: number = 1;
  pageSize: number = 8;
  totalPages: number = 0;

  constructor(private userService: UserService, private router: Router) {} // Injecter Router

  ngOnInit() {
    this.loadUsers();
  }

  // Charger tous les utilisateurs
  loadUsers() {
    this.userService.getAllUsers().subscribe(
      (data) => {
        console.log('Utilisateurs chargés:', data); // Vérifiez la structure des données ici
        this.users = data.map(user => ({
          ...user,
          roles: user.roles ? user.roles.split(',') : [] // Convertir la chaîne en tableau
        }));
        this.totalPages = Math.ceil(this.users.length / this.pageSize);
        this.updatePagedUsers();
      },
      (error) => {
        console.error('Erreur lors du chargement des utilisateurs', error);
      }
    );
  }

  // Mettre à jour les utilisateurs affichés pour la page actuelle
  updatePagedUsers() {
    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.pagedUsers = this.users.slice(startIndex, endIndex);
  }

  // Aller à une page spécifique
  goToPage(page: number) {
    if (page < 1 || page > this.totalPages) return;
    this.currentPage = page;
    this.updatePagedUsers();
  }

  // Supprimer un utilisateur
  deleteUser(id: string) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?')) {
      this.userService.deleteUser(id).subscribe(
        () => {
          this.users = this.users.filter((user) => user.id !== id);
          this.updatePagedUsers();
        },
        (error) => {
          console.error('Erreur lors de la suppression de l\'utilisateur', error);
        }
      );
    }
  }

  // Modifier un utilisateur (redirige vers la page d'édition)
  editUser(id: string) {
    this.router.navigate([`/admin/users/edit/${id}`]);
  }

  // Total des pages pour la pagination
  get totalPagesArray(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i + 1);
  }
}
