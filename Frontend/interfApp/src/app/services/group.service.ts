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

  getMyGroups(id: any): void {

  }

  getAllGroups(): Observable<any>{
    return this.http.get<any[]>(this.apiUrl);
  }

  addGroup(group: any): Observable<any> {
    // convert a Map into an Object so it can be convert into a JSON structure 
    if (group.listSubGroups instanceof Map) {
      const listSubGroupsObject: { [key: string]: any } = {};
      group.listSubGroups.forEach((value : any , key : any) => {
        listSubGroupsObject[key] = value;
      });
      group.listSubGroups = listSubGroupsObject;
    }
    return this.http.post<any>(this.apiUrl, group);
  }

  deleteGroup(id: any) {
    return this.http.delete<any[]>(`${this.apiUrl}/${id}`);
  }

  updateGroup(group: any): Observable<any> {
    if (group.listSubGroups instanceof Map) {
      const listSubGroupsObject: { [key: string]: any } = {};
      group.listSubGroups.forEach((value : any , key : any) => {
        listSubGroupsObject[key] = value;
      });
      group.listSubGroups = listSubGroupsObject;
    }
    console.log(`${this.apiUrl}/${group.id}`);
    return this.http.put<any>(`${this.apiUrl}/${group.id}`, group);
  }

  getGroupById(id: string): Observable<any> {
    return this.http.get<any[]>(`${this.apiUrl}/${id}`);
  }

  addUserToGroup(groupId: any, memberEmail: any) {
    return this.http.put<any[]>(`${this.apiUrl}/member/${memberEmail}/${groupId}`, memberEmail );
  }

  addManagerToGroup(groupId: any, managerId: any) {
    return this.http.put<any[]>(`${this.apiUrl}/manager/${managerId}/${groupId}`,managerId);
  }

  deleteMemberFromGroup(groupId: any, memberId: any){
    return this.http.delete<any[]>(`${this.apiUrl}/member/${memberId}/${groupId}`);
  }

  deleteManagerFromGroup(groupId: any, memberId: any){
    return this.http.delete<any[]>(`${this.apiUrl}/manager/${memberId}/${groupId}`);
  }
}
