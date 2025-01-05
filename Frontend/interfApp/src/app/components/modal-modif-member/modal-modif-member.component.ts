import { CommonModule, KeyValue } from '@angular/common';
import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-modal-modif-member',
  imports: [CommonModule,ReactiveFormsModule],
  templateUrl: './modal-modif-member.component.html',
  styleUrl: './modal-modif-member.component.css'
})
export class ModalModifMemberComponent {
  @Input() member : any;
  @Input() subGroups: Map<string,string[]> = new Map(); 
  @Output() deleteFromGroup = new EventEmitter<any>(); // emits the ID to delete
  @Output() givePermission = new EventEmitter<any>(); // emits the ID to authorize as manager
  @Output() save = new EventEmitter<any>(); // Emits the selected ID when clicking Save

  formSubGroup: FormGroup;

  get SubGroupCustom() : Map<string,string[]>{
    return new Map(
      Array.from(this.subGroups.entries()).slice(2) 
    );
  }
     
  constructor(private formBuilder: FormBuilder) {
    this.formSubGroup = this.formBuilder.group({
      inputSubGroups: [],
    });
  }

  ngOnChanges(changes: SimpleChanges): void {
      if (changes['member'] && this.member) {
        this.formSubGroup = this.formBuilder.group({
          inputSubGroups: [this.getKeysWithValue(this.subGroups,this.member)],
        });
      }
    }

  onDeleteFromGroup() {
    this.deleteFromGroup.emit(this.member);
  }

  onGivePermission() {
    this.givePermission.emit(this.member);
  }

  onSave() {
    // Create an independent copy of the Map
    let newSubGroups = new Map<string, string[]>(
      Array.from(this.subGroups.entries()).map(([key, value]) => [key, [...value]])
    );
    // Browse custom subgroups
    this.SubGroupCustom.forEach((value, key) => {
      const isSelected = this.formSubGroup.value.inputSubGroups.filter((subGroupSelect : any) => subGroupSelect === key).length == 1; // Vérifie si sélectionné
      const isMemberInSubGroup =this.subGroups.get(key)?.filter((member : any) => member === this.member).length == 1; // Vérifie si déjà présent
      if (isSelected && !isMemberInSubGroup) {
        // Add member if selected but not yet present
        const updatedValue = newSubGroups.get(key) || [];
        updatedValue.push(this.member);
        newSubGroups.set(key, updatedValue);
      } else if (!isSelected && isMemberInSubGroup) {
        // Remove member if not selected but present

        const updatedValue = newSubGroups.get(key)?.filter(member => member !== this.member) || [];
        newSubGroups.set(key, updatedValue);
      }
    });
    this.save.emit(newSubGroups); // emits change
  }

  getKeysWithValue(map: Map<string, string[]>, searchString: string): string[] {
    return Array.from(map.entries())
      .slice(2)
      .filter(([key, values]) => values.includes(searchString)) 
      .map(([key]) => key);
  }

  isManager(){
    return this.subGroups.get("Managers")?.filter(member => member === this.member).length == 1;
  }
}
