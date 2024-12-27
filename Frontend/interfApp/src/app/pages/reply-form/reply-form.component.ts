import { CommonModule } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FormlyBootstrapModule } from '@ngx-formly/bootstrap';
import { FormlyFieldConfig, FormlyFormOptions, FormlyModule } from '@ngx-formly/core';
import { AnswerService } from '../../services/answer.service';
import { FormService } from '../../services/form.service';

@Component({
    selector: 'app-reply-form',
    standalone : true,
    imports: [CommonModule, ReactiveFormsModule, FormsModule, FormlyBootstrapModule, FormlyModule,],
    templateUrl: './reply-form.component.html',
    styleUrl: './reply-form.component.css'
})
export class ReplyFormComponent implements OnInit {
  idForm! : number;
  title! : String;
  questions! : FormGroup[];
  formReply = new FormGroup({});
  model: any = {};
  options: FormlyFormOptions = {};
  fields: FormlyFieldConfig[] = [];

  readonly mapType : Map<String,String> = new Map([
    ["Short Answer", "input"],
    ["Open Answer", "textarea"],
    ["Checkbox", "checkbox"],
    ["Multiple choice", "select"],
    ["Date question" , "date"],
    ["Date and time question" , "datetime-local"],
    ["Email question" , "email"],
    ["Number question" , "number"],
    ["Range question" , "range"],
    ["Month question" , "month"],
    ["Time question" , "time"],
    ["Ask for a phone number" , "tel"],
    ["Week question" , "week"],
    ["Color question" , "color"],
    ["Ask coordonates" , "map"],
  ]);

  constructor( private route: ActivatedRoute, private answerService: AnswerService, private formService: FormService) {}

  ngOnInit(){
    const formId = this.route.snapshot.paramMap.get('id');
    if (formId) {
      this.formService.getFormById(formId).subscribe(
        (data) => {
          this.idForm = data.id;
          this.title = data.title;
          this.questions = data.questions;
          this.fields = this.transformFormGroupIntoFormlyField();
        },
        (error) => {
          console.error('Error loading form:', error);
        }
      );
    }
  }

  transformFormGroupIntoFormlyField(): FormlyFieldConfig[] {
    const formlyFields: FormlyFieldConfig[] = [];

    console.log(this.questions);

    this.questions.forEach((question: any , index : number) => {
      const field: FormlyFieldConfig = {
        key: question.inputTitleQuestion || `Question_${index}`,
        type: this.mapType.get(question.inputTypeQuestion) as string || 'input',
        templateOptions: {
          label: question.inputQuestion,
          required: question.inputRequired,
          multiple: question.inputAnswerMultiple,
          selectAllOption: 'Select All',
          options: (question.inputChoices || []).map((choice: string) => ({ value: choice, label: choice })),
        },
        validation: {
          messages: {
            required: 'This field is required', // Message d'erreur pour les champs obligatoires
          },
        },
      };

      formlyFields.push(field);
    });

    return formlyFields;
  }
  submit(){
    console.log(this.formReply.value);

    if (this.formReply.valid) {
      const Data = {
        idForm: this.idForm,
        idUser: "gestion user TO DO",
        answer: this.formReply.value
      };

      // Logique pour sauvegarder le formulaire
      this.answerService.saveAnswer(Data).subscribe({
        next: (response) => {
          console.log('Form saved successfully:', response);
          alert('Réponses sauvegardé avec succès !');
        },
        error: (err) => {
          console.error('Error saving form:', err);
        },
      });
    }
  }
}
