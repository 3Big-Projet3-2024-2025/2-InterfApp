import { Component, EventEmitter, Output } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-modal-confirm',
  standalone: true,
  imports: [],
  templateUrl: './modal-confirm.component.html',
  styleUrl: './modal-confirm.component.css'
})
export class ModalConfirmComponent {
  @Output() confirmEmitter = new EventEmitter<string>();
}
