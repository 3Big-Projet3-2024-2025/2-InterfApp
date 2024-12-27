import { Component } from '@angular/core';
import { GroupService } from '../../services/group.service';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-list-group-page',
  imports: [CommonModule, RouterModule],
  templateUrl: './list-group-page.component.html',
  styleUrl: './list-group-page.component.css'
})
export class ListGroupPageComponent {
  groups: any[] = [];

  constructor(private groupService: GroupService) {}

  ngOnInit(): void {
    this.groupService.getAllGroups().subscribe(
      (data) => {
        this.groups = data;
      },
      (error) => {

      }
    );
  }
}
