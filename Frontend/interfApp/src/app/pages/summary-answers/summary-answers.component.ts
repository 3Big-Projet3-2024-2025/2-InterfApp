import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormService } from '../../services/form.service';
import { AnswerService } from '../../services/answer.service';
import {  CommonModule, KeyValue } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { count } from 'console';
import { MapAnswersComponent } from '../../components/map-answers/map-answers.component';

@Component({
  selector: 'app-summary-answers',
  imports: [CommonModule, ReactiveFormsModule, MapAnswersComponent],
  templateUrl: './summary-answers.component.html',
  styleUrl: './summary-answers.component.css'
})
export class SummaryAnswersComponent implements OnInit {

  form: any;
  everyAnswersOfForm: any[] = [];
  mapAnswerSummarize: Map<any,any[]> = new Map([]) ; //creation of the map for the question, and then the tables of the answers of all users

  constructor(private route: ActivatedRoute, private formService: FormService, private answerService : AnswerService) 
  {
  }

  ngOnInit(): void{
    //retrieve the id of the form from the route settings 
    const formId = this.route.snapshot.paramMap.get('id');
    if (formId) {
      //get the form by its id
      this.formService.getFormById(formId).subscribe(
        (data) => {
          this.form = data;
              // get the answers of the form by the form's id 
          this.answerService.getAnswerByIdForm(formId).subscribe(
            (data) => {
              this.everyAnswersOfForm = data;
              this.Summarization();
              console.log(this.mapAnswerSummarize);
            },
            (error) => {
              console.error('impossible de récupérer le formulaire', error);
              alert('impossible de récupérer le formulaire');
            }
      )
        },
        (error) => {
          console.error('impossible de récupérer le formulaire', error);
          alert('impossible de récupérer le formulaire');
        }
      )
    }
  }

  //create a map of answers by question
  Summarization(){
    // get every questions
    this.form.questions.forEach((question:any, index:any) => {
      // get every answers by user from a question in particular
      
      if(question.inputTypeQuestion == 'Multiple choice' || question.inputTypeQuestion == 'Checkbox') {
        let mapAnswerMultiple: Map<any,number> = new Map([]);
        if(question.inputTypeQuestion == 'Checkbox') {
            question.inputChoices = ['false', 'true'];
            mapAnswerMultiple.set(false,0);
            mapAnswerMultiple.set(true,0);
        } else {
              mapAnswerMultiple = new Map(question.inputChoices.map((choice :any) => [choice,0]));
              }
            this.everyAnswersOfForm.forEach(answersMultipleOfForm => {
              let answerOfQuestion = answersMultipleOfForm.answer['Question_'+index];
              if (answerOfQuestion !== undefined) {
                let countAnswer = mapAnswerMultiple.get(answerOfQuestion);

                if(countAnswer!== undefined) {
                  mapAnswerMultiple.set(answerOfQuestion, countAnswer+1);
                }
              }
            });
        // Crée une structure pour stocker le nombre d'occurrences
        this.mapAnswerSummarize.set(question, Array.from(mapAnswerMultiple.values())); 
      } else {
        let tabAnswersOfQuestion: any[] = [];
      this.everyAnswersOfForm.forEach(answersOfForm => {
        let answerOfQuestion = answersOfForm.answer['Question_'+index];
        if(answerOfQuestion) {
          tabAnswersOfQuestion.push(answerOfQuestion);
        }
      });
      // Crée une structure pour stocker le nombre d'occurrences
      this.mapAnswerSummarize.set(question,tabAnswersOfQuestion);
      }
      
      
    });
  }

  minValue(tabNumberRange:number[]){
    return Math.min(...tabNumberRange);
  }

  Maxvalue(tabNumberRange:number[]){
    return Math.max(...tabNumberRange);
  }

  AverageValue(tabNumberRange:number[]){
    return tabNumberRange.reduce((sum: number, num: number) => Number(sum) + Number(num), 0) / tabNumberRange.length;
  }
}
