import {Component, OnInit} from '@angular/core';
import {GroupService} from "../../services/group.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ActivatedRoute, Router, RouterModule, RouterOutlet} from "@angular/router";
import { CommonModule, KeyValue, KeyValuePipe } from '@angular/common';
import { FormService } from '../../services/form.service';
import { ModalConfirmComponent } from '../../components/modal-confirm/modal-confirm.component';
import { ModalSubGroupComponent } from '../../components/modal-sub-group/modal-sub-group.component';
import { UserService } from '../../services/user.service';
import { ModalModifSubGroupComponent } from '../../components/modal-modif-sub-group/modal-modif-sub-group.component';

declare var bootstrap: any;

@Component({
  selector: 'app-group-page',
  standalone: true,
  imports: [FormsModule,CommonModule, RouterModule, ModalConfirmComponent, ModalSubGroupComponent, ModalModifSubGroupComponent],
  templateUrl: './group-page.component.html',
  styleUrl: './group-page.component.css'
})
export class GroupPageComponent implements OnInit {

  groupId: string | null = null;
  group: any;
  listSubGroups : Map<string,string[]> = new Map();
  expandedSubGroups: boolean[] = [];
  subGroupModif : KeyValue<string, string[]> | undefined ;

  forms: any[] = [];
  expandedForms: boolean[] = [];
  formToDelete : string = "";

  members : any [] = [];

  constructor(private route: ActivatedRoute,private groupService: GroupService, private formService : FormService, private userService : UserService) {}

  ngOnInit(): void {
    this.groupId = this.route.snapshot.paramMap.get('id');
    this.loadGroups();
    this.loadForms();
  }

  loadGroups(){
    if (this.groupId) {
      this.groupService.getGroupById(this.groupId).subscribe(
          (data) => {
            this.group = data;
            this.group.listSubGroups = new Map(Object.entries(this.group.listSubGroups));
            this.loadMembers();
            this.group.listSubGroups.forEach(() => {
              this.expandedForms.push(false);
            });
            this.listSubGroups = this.group.listSubGroups;
          },
          (error) => {
          }
        );
    }
  }

  loadForms(){
    this.formService.getAllForms().subscribe(
      (data) => {
        this.forms = data;
        this.expandedForms = this.forms.map(() => false)
      },
      (error) => {
      }
    );
  }

  loadMembers(){
    const uniqueElementsSet = new Set<number>();

    this.group.listSubGroups.forEach((sublist : any) => {
      sublist.forEach((element : any) => uniqueElementsSet.add(element));
    });

    Array.from(uniqueElementsSet).forEach((idUser : any) =>{
      this.userService.getUserById(idUser).subscribe(
        (data) => {
          this.members.push(data);
        }
      );
    });
  }

  deleteForm(){
    this.formService.deleteForm(this.formToDelete).subscribe();
    this.loadForms();
  }

  createSubGroup(name :any){
    this.group.listSubGroups.set(name,[]);
    this.groupService.updateGroup(this.group).subscribe(
      (data) => {
        this.group = data;
        this.group.listSubGroups = new Map(Object.entries(this.group.listSubGroups));
        this.group.listSubGroups.forEach(() => {
          this.expandedForms.push(false);
        });
        this.listSubGroups = this.group.listSubGroups;
      },
      (error) => {
      }
    );
  }

  saveSubGroup(subGroup : any){
    this.subGroupModif = undefined;
  }


  getUserNameById(id : string) : string{
    const user = this.members.find(u => u.id === id);
    return user ? user.username : 'Utilisateur inconnu';
  }
}
