import { Component, OnInit } from '@angular/core';
import { FormService } from '../../services/form.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
    selector: 'app-open-form',
    standalone:true,
    imports: [CommonModule],
    templateUrl: './open-form.component.html',
    styleUrl: './open-form.component.css'
})
export class OpenFormComponent implements OnInit {
  forms: any[] = [];
  selectedForm: any;

  constructor(private formService: FormService, private router: Router) {}

  ngOnInit(): void {
    this.loadForms();
  }

  loadForms(): void {
    this.formService.getAllForms().subscribe(
      (data) => {
        this.forms = data;
      },
      (error) => {
        console.error('Error loading forms:', error);
      }
    );
  }

  respondToForm(formId: string): void {
    this.router.navigate([`/form/${formId}`]);
  }
}
