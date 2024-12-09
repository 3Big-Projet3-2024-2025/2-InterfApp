import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {Group} from "../../models/group";
import {User} from "../../models/User";

@Component({
  selector: 'app-create-group-page',
  standalone: true,
  imports: [],
  templateUrl: './modal-create-group.component.html',
  styleUrl: './modal-create-group.component.css'
})
export class ModalCreateGroupComponent {
  administrator: User = {
    id: 0,
    email: '',
    username: '',
    password: '',
    roles: []
  }

  groupToAdd: Group = {
    id: 0,
    name: '',
    administrator: this.administrator,
    membres: []
  }
  constructor(private router: Router) {
  }


  addGroup() {
    this.router.navigate(['/group']);
  }

  addUsersToGroup() {

  }
}
