import {Component, Input} from '@angular/core';
import {Group} from "../../models/Group";

@Component({
  selector: 'app-modal-sub-group',
  standalone: true,
  imports: [],
  templateUrl: './modal-sub-group.component.html',
  styleUrl: './modal-sub-group.component.css'
})
export class ModalSubGroupComponent {
  @Input() group?: Group;
}
