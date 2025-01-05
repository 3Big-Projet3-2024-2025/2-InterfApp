import { CommonModule, KeyValue } from '@angular/common';
import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-modal-modif-sub-group',
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './modal-modif-sub-group.component.html',
  styleUrl: './modal-modif-sub-group.component.css'
})
export class ModalModifSubGroupComponent {
  @Input() members : any [] = [];
  @Input() subGroup :  KeyValue<string, string[]> | undefined ;
  @Output() subGroupEmitter = new EventEmitter<string>();

  formSubGroup: FormGroup;
   
  constructor(private formBuilder: FormBuilder) {
    this.formSubGroup = this.formBuilder.group({
      inputSubGroupName: ['', [Validators.required]],
      inputMembers: ['']
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['subGroup'] && this.subGroup) {
      this.formSubGroup = this.formBuilder.group({
        inputSubGroupName: [this.subGroup.key, [Validators.required]],
        inputMembers: [this.subGroup.value]
      });
    }
  }
   
  saveSubGroup() {
    if (this.formSubGroup.valid){
      this.subGroupEmitter.emit(this.formSubGroup.value);
    }
    this.formSubGroup.get('inputSubGroup')?.reset();
  }

  deleteSubGroup(){
    this.subGroupEmitter.emit();
  }
}
