import { Component } from '@angular/core';
import {User} from "../../models/User";
import {Group} from "../../models/Group";
import {Router} from "@angular/router";
import {ModalInviteMembersComponent} from "../../components/modal-invite-members/modal-invite-members.component";
import {ModalConfirmComponent} from "../../components/modal-confirm/modal-confirm.component";

@Component({
  selector: 'app-create-group-pages',
  standalone: true,
  imports: [
    ModalInviteMembersComponent,
    ModalConfirmComponent
  ],
  templateUrl: './create-group-page.component.html',
  styleUrl: './create-group-page.component.css'
})
export class CreateGroupPageComponent {
  manager: User = {
    id: 0,
    email: '',
    username: '',
    password: '',
    roles: []
  }

  groupToAdd: Group = {
    id: 0,
    name: '',
    managers: [this.manager],
    members: [],
    subGroups: []
  }
  constructor(private router: Router) {
  }


}
