import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Group} from "../models/group";

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
}
