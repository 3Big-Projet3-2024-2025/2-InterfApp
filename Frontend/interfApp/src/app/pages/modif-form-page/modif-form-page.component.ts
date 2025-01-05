import { CommonModule } from '@angular/common';
import { Component, OnInit, QueryList, ViewChildren } from '@angular/core';
import { ReactiveFormsModule, FormsModule, FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionComponent } from '../../components/question/question.component';
import { FormService } from '../../services/form.service';

@Component({
  selector: 'app-modif-form-page',
  imports: [QuestionComponent, CommonModule,ReactiveFormsModule,FormsModule],
  templateUrl: './modif-form-page.component.html',
  styleUrl: './modif-form-page.component.css'
})
export class ModifFormPageComponent implements OnInit {
  questions: any[] = [];
    errorMessage : string = ""
    formForm : FormGroup
    groupId : any ;
  
    @ViewChildren(QuestionComponent) questionComponents!: QueryList<QuestionComponent>;
  
    constructor(private formBuilder: FormBuilder, private formService: FormService,private route: ActivatedRoute, private router : Router){
      this.formForm = this.formBuilder.group({
        inputTitreForm:['', Validators.required],
        arrayFormQuestion: this.formBuilder.array([]),
      })
    }

    ngOnInit(): void {
      const formId = this.route.snapshot.paramMap.get('id');
      if (formId){
        this.formService.getFormById(formId).subscribe(
          (data) => {
            this.questions = data.questions;
            this.groupId = data.idGroup;
            this.formForm = this.formBuilder.group({
              inputTitreForm:[data.title, Validators.required],
              arrayFormQuestion: this.formBuilder.array([]),
            })
          },
          (error) => {
          }
        );
      }
    }
  
    get arrayFormQuestion(): FormArray {
      return this.formForm.get('arrayFormQuestion') as FormArray;
    }
  
    addQuestion(): void {
      const newId  = this.questions[this.questions.length - 1] + 1;
      this.questions.push(newId); // add a new question
    }
  
    removeQuestion(index: number): void {
      if (this.questions.length > 1) {
        this.questions.splice(index, 1); // delete the question at the giving index
      }
    }
  
    requestForms(): void {
      this.arrayFormQuestion.clear(); // Empty the table to be able to update the data
      this.questionComponents.toArray().forEach((questionComp) => {
        questionComp.emitFormGroup(); // Ask each child to provide their FormGroup
      });
    }
  
    saveForm(form: FormGroup): void {
      this.arrayFormQuestion.push(form); // Adds the FormGroup to the list
  
    }
  
    move(isupwards : boolean , id : number){
      if(isupwards){
        if(id != 0){
          [this.questions[id - 1], this.questions[id]] = [this.questions[id], this.questions[id - 1]];
        }
      }else if(id != this.questions.length - 1){
        [this.questions[id ], this.questions[id + 1]] = [this.questions[id + 1], this.questions[id]];
      }
    }
  
  
    saveQuestions(): void {
      this.requestForms();
  
      if (this.formForm.valid) {
        this.errorMessage = "";
        const formData = {
          id:this.route.snapshot.paramMap.get('id'),
          title: this.formForm.get('inputTitreForm')?.value,
          questions: this.formForm.get('arrayFormQuestion')?.value,
          idGroup: this.groupId
        };
  
        // save the form
        this.formService.updateForm(formData).subscribe({
          next: (response) => {
            console.log('Form saved successfully:', response);
            alert('Formulaire sauvegardé avec succès !');
            this.router.navigate(['/group/' + this.groupId]);
          },
          error: (err) => {
            console.error('Error saving form:', err);
            this.errorMessage = 'Erreur lors de la sauvegarde du formulaire.';
          },
        });
      } else {
        this.errorMessage = "The form is not complete";
      }
    }
}
