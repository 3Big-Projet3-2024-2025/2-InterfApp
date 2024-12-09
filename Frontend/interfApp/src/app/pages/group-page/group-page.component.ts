import {Component, OnInit} from '@angular/core';
import {Group} from "../../models/group";
import {GroupeService} from "../../services/groupe.service";
import {FormsModule} from "@angular/forms";
import {Router, RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-group-page',
  standalone: true,
  imports: [
    FormsModule,
    RouterOutlet
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

  groupes: Group[] = [];

  mesGroupes: Group[] = [];

  groupeTest: Group = {
    id: 1,
    name: 'Groupe 1',
    membres: [],
    administrateur: this.userTest
  }
  groupeTest2: Group = {
    id: 2,
    name: 'Groupe 2',
    membres: [],
    administrateur: this.userTest
  }

  expandedGroups: Set<number> = new Set<number>();

  constructor(private groupeService: GroupeService, private router: Router) {
  }
  ngOnInit(): void {
    this.mesGroupes.push(this.groupeTest);
    this.mesGroupes.push(this.groupeTest2);
    this.groupeTest.membres.push(this.userTest);
    this.groupeTest.membres.push(this.userTest2);
    this.groupeTest.membres.push(this.userTest3);
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
