import {Component, OnInit} from '@angular/core';
import {Group} from "../../models/Group";
import {GroupService} from "../../services/group.service";
import {FormsModule} from "@angular/forms";
import {Router, RouterOutlet} from "@angular/router";
import {ModalSubGroupComponent} from "../../components/modal-sub-group/modal-sub-group.component";
import {SubgroupComponent} from "../../components/subgroup/subgroup.component";

@Component({
  selector: 'app-group-page',
  standalone: true,
  imports: [
    FormsModule,
    RouterOutlet,
    ModalSubGroupComponent,
    SubgroupComponent
  ],
  templateUrl: './group-page.component.html',
  styleUrl: './group-page.component.css'
})
export class GroupPageComponent implements OnInit {

  groups: Group[] = [];

  myGroups: Group[] = [];

  groupById: Group = {
    id: "",
    name: '',
    managers: [],
    members: [],
    subGroups: []
  }

  expandedGroups: Set<string> = new Set<string>();

  constructor(private groupService: GroupService, private router: Router) {
  }
  ngOnInit(): void {
    this.groupService.getGroupById("67602b4723a48e04bfbdc612").subscribe(
      (data) => {
        this.groupById = data;
        this.myGroups.push(this.groupById);
      }
    );
  }

  toggleExpand(groupId: string): void {
    console.log(groupId);
    if (this.expandedGroups.has(groupId)) {
      this.expandedGroups.delete(groupId);
    } else {
      this.expandedGroups.clear(); // <- To collapse other expanded groups
      this.expandedGroups.add(groupId);
    }
  }

  isExpanded(groupId: string): boolean {
    return this.expandedGroups.has(groupId);
  }

  createGroup(): void {
    this.router.navigate(['create-group']);
  }
}
