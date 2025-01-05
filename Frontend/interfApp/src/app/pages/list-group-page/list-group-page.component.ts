import { Component } from '@angular/core';
import { GroupService } from '../../services/group.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CookieService } from 'ngx-cookie-service';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-list-group-page',
  imports: [CommonModule, RouterModule, ReactiveFormsModule, FormsModule],
  templateUrl: './list-group-page.component.html',
  styleUrl: './list-group-page.component.css'
})
export class ListGroupPageComponent {
  groups: any[] = [];
  filteredGroups: any[] = []; // filtered list by research
  userNamesMap: { [key: string]: string } = {}; 
  currentPage: number = 1; // actual page
  groupPerPage: number = 9; // number of group per page
  searchQuery: string = ''; // research querry 
  noResultsFound: boolean = false; // Indicateur d'absence de résultats

  constructor(private groupService: GroupService, private userService: UserService , private cookieService : CookieService) {}

  ngOnInit(): void {
    if (this.cookieService.get('jwt') !== "") {
      const tokenJWT = jwtDecode(this.cookieService.get('jwt')) as any;
      this.userService.getUserById(tokenJWT.id).subscribe(
        (user) => {
          const groupRequests = user.listGroup.map((groupId: any) =>
            this.groupService.getGroupById(groupId).toPromise()
          );
  
          Promise.all(groupRequests).then(
            (groups) => {
              this.groups = groups;
              this.filteredGroups = [...this.groups];
              this.preloadUserNames();
            },
            (error) => {
              console.error("Erreur lors de la récupération des groupes", error);
            }
          );
        },
        (error) => {
          console.error("Erreur lors de la récupération de l'utilisateur", error);
        }
      );
    }
  }

  preloadUserNames(): void {
    this.groups.forEach((group) => {
      const managerId = group.listSubGroups["Managers"][0];
      if (managerId && !this.userNamesMap[managerId]) {
        this.userService.getUserById(managerId).subscribe(
          (user) => {
            this.userNamesMap[managerId] = user.username;
          },
          (error) => {
            console.error(`Erreur lors de la récupération de l'utilisateur avec l'id ${managerId}`, error);
          }
        );
      }
    });
  }
  
  // to separage the groups of each page
  getPaginatedGroups(): any[] {
    const startIndex = (this.currentPage - 1) * this.groupPerPage;
    return this.filteredGroups.slice(startIndex, startIndex + this.groupPerPage);
  }

  goToPage(page:number){
    if (page > 0 && page <= this.totalPages()) {
      this.currentPage = page;
    }
  }

  // calculate de number of pages 
   totalPages(): number {
    return Math.ceil(this.groups.length / this.groupPerPage);
  }

   // Filtrer les groupes sur clic du bouton
  filterGroups(): void {
    if (this.searchQuery.trim() === '') {
      // Si la recherche est vide, afficher tous les groupes
      this.filteredGroups = [...this.groups];
    }
    else {
      // Filtrer les groupes
      this.filteredGroups = this.groups.filter((group) =>
        group.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
    this.currentPage = 1; // Réinitialiser à la première page
     // Vérifier si des groupes ont été trouvés
     if (this.filteredGroups.length === 0) {
      this.noResultsFound = true; // Mettre à jour la variable pour indiquer qu'il n'y a pas de résultats
    } else {
      this.noResultsFound = false; // Réinitialiser si des résultats sont trouvés
    }

    this.currentPage = 1; // Réinitialiser à la première page après filtrage
  }

  
}
