import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-modal-invite-members',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './modal-invite-members.component.html',
  styleUrl: './modal-invite-members.component.css'
})
export class ModalInviteMembersComponent {

  @Output() emailEmitter = new EventEmitter<string>();
  formEmail: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.formEmail = this.formBuilder.group({
      inputEmail: ['', [Validators.email,Validators.required]]
    });
  }

  addEmail() {
    if (this.formEmail.valid){
      this.emailEmitter.emit(this.formEmail.value.inputEmail);
    }
    this.formEmail.get('inputEmail')?.reset();
  }
}
