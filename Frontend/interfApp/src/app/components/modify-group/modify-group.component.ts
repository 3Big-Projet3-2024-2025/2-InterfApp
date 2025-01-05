import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { GroupService } from '../../services/group.service';
import { ModalInviteMembersComponent } from '../modal-invite-members/modal-invite-members.component';
import { CommonModule } from '@angular/common';
import { Modal } from 'bootstrap';
import { tick } from '@angular/core/testing';

@Component({
  selector: 'app-modify-group',
  standalone: true,
  imports: [ModalInviteMembersComponent,CommonModule, ReactiveFormsModule],
  templateUrl: './modify-group.component.html',
  styleUrl: './modify-group.component.css'
})
export class ModifyGroupComponent {
  @Input() group :  any;
  @Output() saveEmitter = new EventEmitter<any>();
  @Output() deleteEmitter = new EventEmitter<any>();
  
  formGroup: FormGroup;
  emailList: string[] = [];
  
  
  constructor(private router: Router , private formBuilder: FormBuilder, private groupService: GroupService) {
    this.formGroup = this.formBuilder.group({
      inputName: ['', [Validators.required]]
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
      if (changes['group'] && this.group) {
        this.formGroup = this.formBuilder.group({
          inputName: [this.group.name, [Validators.required]]
        });
      }
    }
  
  removeEmail(index : number ){
    this.emailList.splice(index, 1);
  }

  addEmail(email : string){
    this.emailList.push(email);
    if (typeof window !== 'undefined' && typeof document !== 'undefined') {
      const modalElement = document.getElementById('groupSettingsModal');
      if (modalElement) {
          const groupSettingsModal = new window.bootstrap.Modal(modalElement);
          groupSettingsModal.show();
      }
    }
  }

  saveGroup(){
    if (this.formGroup.valid) {
      if(this.emailList.length > 0){
        this.emailList.forEach(memberEmail => {
          this.groupService.addUserToGroup(this.group.id,memberEmail).subscribe((value) => {});
        });
      }
      if(this.group.name != this.formGroup.value.inputName){
        this.group.name = this.formGroup.value.inputName;
        this.saveEmitter.emit(this.group);
      } 
    }
  }

  deleteGroup(){
    this.deleteEmitter.emit();
  }
}
