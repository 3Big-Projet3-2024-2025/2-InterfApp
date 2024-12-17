import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FieldType, FormlyModule } from '@ngx-formly/core';


@Component({
  selector: 'app-formly-generic-type-module',
  imports: [CommonModule, ReactiveFormsModule, FormsModule, FormlyModule],
  templateUrl: './formly-generic-type-module.component.html',
  styleUrl: './formly-generic-type-module.component.css'
})
export class FormlyGenericTypeModuleComponent extends  FieldType{
  get control(): FormControl {
    return this.formControl as FormControl;
  }
}
