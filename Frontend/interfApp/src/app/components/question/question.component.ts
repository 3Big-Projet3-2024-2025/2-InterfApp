import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, Validators,  ReactiveFormsModule, FormArray, FormControl } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
    selector: 'app-question',
    standalone: true,
    imports: [FormsModule, CommonModule, ReactiveFormsModule],
    templateUrl: './question.component.html',
    styleUrl: './question.component.css'
})
export class QuestionComponent {
  @Input() questionId!: number; // Reçoit un identifiant de question depuis le parent
  @Output() remove = new EventEmitter<void>(); // Émet un événement pour supprimer la question
  @Output() formEmitter = new EventEmitter<FormGroup>();
  @Output() moveEmitter = new EventEmitter<boolean>();
  formQuestion: FormGroup;
  isContentHidden : boolean = false;
  questionTypes: string[] = ["Short Answer","Open Answer","Checkbox","Multiple choice", "Date question","Date and time question" ,"Email question" ,"Number question" ,"Range question" ,"Month question" , "Time question" , "Ask for a phone number","Week question", "Color question", "Ask coordonates"  ];
  choices: string[] = ["",""];
  InputChoicesArray : FormArray = this.formBuilder.array(this.choices.map(choice => this.formBuilder.control(choice)));
  AnswerMultiple : FormControl = new FormControl(false);

  constructor(private formBuilder: FormBuilder){
    this.formQuestion = this.formBuilder.group({
      inputRequired:[true],
      inputQuestion:['', Validators.required],
      inputTypeQuestion: ['', Validators.required],
    })
  }

  get inputChoicesArray(){
    return (this.formQuestion.get('inputChoices') as FormArray);
  }

  addChoice(): void {
    this.choices.push("");
    this.inputChoicesArray.push(this.formBuilder.control('', Validators.required));
  }

  removeChoice(index: number): void {
    if (this.choices.length > 1) {
      this.choices.splice(index, 1);
      this.inputChoicesArray.removeAt(index);
    }
  }

  removeQuestion(): void {
    this.remove.emit();
  }

  onTypeChange(typeQuestion : string):void{
    if (typeQuestion == "Multiple choice"){
      this.formQuestion.addControl('inputChoices', this.InputChoicesArray);
      this.formQuestion.addControl('inputAnswerMultiple', this.AnswerMultiple);
    }else{
      this.formQuestion.removeControl("inputChoices");
      this.formQuestion.removeControl("inputAnswerMultiple");
    }
  }

  emitFormGroup(): void {
    this.formEmitter.emit(this.formQuestion);
  }

  moveUp(){
    this.moveEmitter.emit(true);
  }

  moveDown(){
    this.moveEmitter.emit(false);
  }

  toggleContent(){
    this.isContentHidden = this.isContentHidden ? false : true ;
  }

}
