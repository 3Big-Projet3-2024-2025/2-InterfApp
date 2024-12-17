import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Group} from "../models/Group";
import {SubGroup} from "../models/SubGroup";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  private apiUrl = 'http://localhost:8080/api/groups';
  constructor(private http : HttpClient) {}

  getMyGroups(id: number): void {

  }

  getAllGroups(): Observable<any>{
    return this.http.get<any[]>(this.apiUrl);
  }

  addGroup(group: Group) {

  }

  deleteGroup(id: number) {

  }

  updateGroup(group: Group) {

  }

  getGroupById(id: string): Observable<any> {
    return this.http.get<any[]>(`${this.apiUrl}/${id}`);
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
