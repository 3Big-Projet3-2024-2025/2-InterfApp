import { Component } from '@angular/core';
import { GroupService } from '../../services/group.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-list-group-page',
  imports: [CommonModule, RouterModule],
  templateUrl: './list-group-page.component.html',
  styleUrl: './list-group-page.component.css'
})
export class ListGroupPageComponent {
  groups: any[] = [];
  user:any[]=[];
  currentPage: number = 1; // actual page
  groupPerPage: number = 9; // number of group per page

  constructor(private groupService: GroupService, private userService: UserService) {}

  ngOnInit(): void {
    this.groupService.getAllGroups().subscribe(
      (data) => {
        this.groups = data;
      },
      (error) => {
        console.error('Erreur lors de la récupération des groupes', error);
      }
    );
  }
  
  // to separage the groups of each page
  getPaginatedGroups(): any[] {
    const startIndex = (this.currentPage - 1) * this.groupPerPage;
    return this.groups.slice(startIndex, startIndex + this.groupPerPage);
  }


 /* getUserName(managerId:any): any{
    if (managerId) {
      this.userService.getUserById(managerId).subscribe(
        (user) => {
          let nameManager = user.username; 
          return nameManager;
        },
        (error) => {
          console.error(`Erreur lors de la récupération de l'utilisateur avec l'id ${managerId}`, error);
        }
      );
    }
  }
*/

  goToPage(page:number){
    if (page > 0 && page <= this.totalPages()) {
      this.currentPage = page;
    }
  }

  // calculate de number of pages 
   totalPages(): number {
    return Math.ceil(this.groups.length / this.groupPerPage);
  }

}
