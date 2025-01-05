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
import { ModalModifMemberComponent } from '../../components/modal-modif-member/modal-modif-member.component';
import { ModifyGroupComponent } from '../../components/modify-group/modify-group.component';
import { CookieService } from 'ngx-cookie-service';
import { jwtDecode } from 'jwt-decode';

declare var bootstrap: any;

@Component({
  selector: 'app-group-page',
  standalone: true,
  imports: [FormsModule,CommonModule, RouterModule, ModalConfirmComponent, ModalSubGroupComponent, ModalModifSubGroupComponent, ModalModifMemberComponent,ModifyGroupComponent],
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
  memberModif : any;

  constructor(private route: ActivatedRoute,private groupService: GroupService, private formService : FormService, private userService : UserService, private router : Router,private cookieService : CookieService) {}

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
    if (this.subGroupModif?.key != subGroup.inputSubGroupName){
      this.group.listSubGroups.delete(this.subGroupModif?.key);
    }
    this.group.listSubGroups.set(subGroup.inputSubGroupName,subGroup.inputMembers);
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


  getUserNameById(id : string) : string{
    const user = this.members.find(u => u.id === id);
    return user ? user.username : 'Utilisateur inconnu';
  }

  deleteMemberFromGroup(idMember : any){
    this.groupService.deleteMemberFromGroup(this.group.id,idMember).subscribe(
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

  givePermissionManager(idMember : any){
    if(this.listSubGroups.get("Managers")?.filter(member => member === idMember).length != 1){
      this.groupService.addManagerToGroup(this.group.id,idMember).subscribe(
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
    }else{
      this.groupService.deleteManagerFromGroup(this.group.id,idMember).subscribe(
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
    
  }

  updateMember(listSubGroups : any){
    this.isManager();
    this.group.listSubGroups = listSubGroups;
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

  deleteGroup(){
    this.groupService.deleteGroup(this.group.id).subscribe();
    this.router.navigate(['group']);
  }

  saveGroupModif(group : any){
    this.groupService.updateGroup(group).subscribe(
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

  isManager(){
    const token = jwtDecode(this.cookieService.get("jwt")) as any;
    if(token.roles.includes("Admin")){
      return true;
    }
    return this.listSubGroups.get("Managers")?.includes(token.id);
  }
}
