import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-modal-confirm',
  standalone: true,
  imports: [],
  templateUrl: './modal-confirm.component.html',
  styleUrl: './modal-confirm.component.css'
})
export class ModalConfirmComponent {
  constructor(private router: Router) {
  }
  addGroup() {

    this.router.navigate(['/group']);
  }
}
