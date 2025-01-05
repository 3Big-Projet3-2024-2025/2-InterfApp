import { Component, input, QueryList, ViewChildren } from '@angular/core';
import { QuestionComponent } from '../../components/question/question.component';
import { CommonModule } from '@angular/common';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormService } from '../../services/form.service';
import { ActivatedRoute, Router } from '@angular/router';
@Component({
  selector: 'app-create-form',
  standalone:true,
  imports: [QuestionComponent, CommonModule,ReactiveFormsModule,FormsModule],
  templateUrl: './create-form.component.html',
  styleUrl: './create-form.component.css'
})
export class CreateFormComponent {
  questions: number[] = [1];
  errorMessage : string = ""
  formForm : FormGroup

  @ViewChildren(QuestionComponent) questionComponents!: QueryList<QuestionComponent>;

  constructor(private formBuilder: FormBuilder, private formService: FormService,private route: ActivatedRoute, private router: Router){
    this.formForm = this.formBuilder.group({
      inputTitreForm:['', Validators.required],
      arrayFormQuestion: this.formBuilder.array([]),
    })
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
        title: this.formForm.get('inputTitreForm')?.value,
        questions: this.formForm.get('arrayFormQuestion')?.value,
        idGroup: this.route.snapshot.paramMap.get('id')
      };

      // save the form
      this.formService.saveForm(formData).subscribe({
        next: (response) => {
          console.log('Form saved successfully:', response);
          alert('Formulaire sauvegardé avec succès !');
          this.router.navigate(['/group/'+ formData.idGroup]);
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
