import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {ModalInviteMembersComponent} from "../../components/modal-invite-members/modal-invite-members.component";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { GroupService } from '../../services/group.service';

@Component({
  selector: 'app-create-group-pages',
  standalone: true,
  imports: [ModalInviteMembersComponent,CommonModule, ReactiveFormsModule],
  templateUrl: './create-group-page.component.html',
  styleUrl: './create-group-page.component.css'
})
export class CreateGroupPageComponent {
  formGroup: FormGroup;
  emailList: string[] = [];


  constructor(private router: Router , private formBuilder: FormBuilder, private groupService: GroupService) {
      this.formGroup = this.formBuilder.group({
        inputName: ['', [Validators.required]]
      });
  }

  removeEmail(index : number ){
    this.emailList.splice(index, 1);
  }

  addEmail(email : string){
    this.emailList.push(email);
  }

  onSubmit(){
    if (this.formGroup.valid) {
      const groupData = {
        name: this.formGroup.value.inputName,
        listSubGroups: new Map([["Members",this.emailList]]),
      };


      this.groupService.addGroup(groupData).subscribe(
        (response) => {
          this.router.navigate(['group']);
          // You can redirect the user or display a success message
          console.log('group crée avec succés', response);
        },
        (error) => {
          console.error(error);
        }
      )
    }
  }


}
