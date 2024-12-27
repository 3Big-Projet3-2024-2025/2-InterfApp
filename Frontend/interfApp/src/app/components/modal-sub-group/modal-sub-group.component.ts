import { CommonModule } from '@angular/common';
import {Component, EventEmitter, Input, Output} from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-modal-sub-group',
  standalone: true,
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './modal-sub-group.component.html',
  styleUrl: './modal-sub-group.component.css'
})
export class ModalSubGroupComponent {
 @Output() subGroupEmitter = new EventEmitter<string>();
   formSubGroup: FormGroup;
 
   constructor(private formBuilder: FormBuilder) {
     this.formSubGroup = this.formBuilder.group({
       inputSubGroup: ['', [Validators.required]]
     });
   }
 
   createSubGroup() {
     if (this.formSubGroup.valid){
       this.subGroupEmitter.emit(this.formSubGroup.value.inputSubGroup);
     }
     this.formSubGroup.get('inputSubGroup')?.reset();
   }
}
