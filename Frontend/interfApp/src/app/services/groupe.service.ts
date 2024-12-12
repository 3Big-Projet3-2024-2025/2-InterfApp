import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Group} from "../models/Group";
import {SubGroup} from "../models/SubGroup";

@Injectable({
  providedIn: 'root'
})
export class GroupeService {

  constructor(private http : HttpClient) { }

  getMyGroups(id: number) {

  }

  getAllGroups() {

  }

  addGroup(group: Group) {

  }

  deleteGroup(id: number) {

  }

  updateGroup(group: Group) {

  }

  getGroupById(id: number) {

  }

  addUsersToGroup(groupId: number, users: number[]) {

  }

  addUsersToGroupCsv(groupId: number, file: File) {

  }

  addManagerToGroup(groupId: number, managerId: number) {

  }

  addSubGroupToGroup(groupId: number, subGroup: SubGroup) {

  }
}
