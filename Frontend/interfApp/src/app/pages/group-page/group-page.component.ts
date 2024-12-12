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

  userTest = {
    id: 0,
    email: "email@test.com",
    username: "Jacques",
    password: "string",
    roles: ["string"]
  }
  userTest2 = {
    id: 0,
    email: "email@test.com",
    username: "Pierre",
    password: "string",
    roles: ["string"]
  }
  userTest3 = {
    id: 0,
    email: "email@test.com",
    username: "Paul",
    password: "string",
    roles: ["string"]
  }

  groups: Group[] = [];

  myGroups: Group[] = [];

  groupeTest: Group = {
    id: 1,
    name: 'Equipe 1',
    members: [],
    managers: [this.userTest],
    subGroups: []
  }
  groupeTest2: Group = {
    id: 2,
    name: 'Equipe RH',
    members: [],
    managers: [this.userTest],
    subGroups: []
  }
  groupeTest3: Group = {
    id: 3,
    name: 'Equipe manager',
    members: [],
    managers: [this.userTest],
    subGroups: []
  }

  subGroupTest = {
    id: 0,
    name: 'Equipe 1',
    members: [this.userTest, this.userTest2]
  }

  subGroupTest2 = {
    id: 1,
    name: 'Equipe 2',
    members: [this.userTest2, this.userTest3]
  }

  subGroupTest3 = {
    id: 1,
    name: 'Equipe chef',
    members: [this.userTest, this.userTest3]
  }

  expandedGroups: Set<number> = new Set<number>();

  constructor(private groupService: GroupService, private router: Router) {
  }
  ngOnInit(): void {
    //hard code of groups and users - to remove later
    this.myGroups.push(this.groupeTest);
    this.myGroups.push(this.groupeTest2);
    this.myGroups.push(this.groupeTest3);
    this.groupeTest.members.push(this.userTest);
    this.groupeTest.members.push(this.userTest2);
    this.groupeTest.members.push(this.userTest3);
    this.groupeTest2.members.push(this.userTest);
    this.groupeTest2.members.push(this.userTest2);
    this.groupeTest2.members.push(this.userTest3);
    this.groupeTest3.members.push(this.userTest);
    this.groupeTest3.members.push(this.userTest2);
    this.groupeTest3.members.push(this.userTest3);
    this.groupeTest.subGroups.push(this.subGroupTest);
    this.groupeTest.subGroups.push(this.subGroupTest2);
    this.groupeTest2.subGroups.push(this.subGroupTest);
    this.groupeTest2.subGroups.push(this.subGroupTest3);
    this.groupeTest3.subGroups.push(this.subGroupTest2);
    this.groupeTest3.subGroups.push(this.subGroupTest3);
    this.groupeTest3.subGroups.push(this.subGroupTest);
    //end of hard coding
  }

  toggleExpand(groupId: number): void {
    console.log(groupId);
    if (this.expandedGroups.has(groupId)) {
      this.expandedGroups.delete(groupId);
    } else {
      this.expandedGroups.clear(); // <- To collapse other expanded groups
      this.expandedGroups.add(groupId);
    }
  }

  isExpanded(groupId: number): boolean {
    return this.expandedGroups.has(groupId);
  }

  createGroup(): void {
    this.router.navigate(['create-group']);
  }
}
