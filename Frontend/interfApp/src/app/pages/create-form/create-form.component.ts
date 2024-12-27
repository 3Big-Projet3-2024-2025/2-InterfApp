import { Component, input, QueryList, ViewChildren } from '@angular/core';
import { QuestionComponent } from '../../components/question/question.component';
import { CommonModule } from '@angular/common';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { FormService } from '../../services/form.service';
import { ActivatedRoute } from '@angular/router';
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

  constructor(private formBuilder: FormBuilder, private formService: FormService,private route: ActivatedRoute){
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
    this.questions.push(newId); // Ajoute une nouvelle question
  }

  removeQuestion(index: number): void {
    if (this.questions.length > 1) {
      this.questions.splice(index, 1); // Supprime la question à l'index donné
    }
  }

  requestForms(): void {
    this.arrayFormQuestion.clear(); // Vide le tableau pour pouvoir update les données
    this.questionComponents.toArray().forEach((questionComp) => {
      questionComp.emitFormGroup(); // Demande à chaque enfant de fournir son FormGroup
    });
  }

  saveForm(form: FormGroup): void {
    this.arrayFormQuestion.push(form); // Ajoute le FormGroup de l'enfant à la liste
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
    console.log(this.formForm.value);

    if (this.formForm.valid) {
      this.errorMessage = "";
      const formData = {
        title: this.formForm.get('inputTitreForm')?.value,
        questions: this.formForm.get('arrayFormQuestion')?.value,
        idGroup: this.route.snapshot.paramMap.get('id')
      };

      // Logique pour sauvegarder le formulaire
      this.formService.saveForm(formData).subscribe({
        next: (response) => {
          console.log('Form saved successfully:', response);
          alert('Formulaire sauvegardé avec succès !');
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
