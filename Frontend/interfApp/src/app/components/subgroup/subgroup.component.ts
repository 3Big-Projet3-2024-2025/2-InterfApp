import {Component, Input} from '@angular/core';
import {SubGroup} from "../../models/SubGroup";

@Component({
  selector: 'app-subgroup',
  standalone: true,
  imports: [],
  templateUrl: './subgroup.component.html',
  styleUrl: './subgroup.component.css'
})
export class SubgroupComponent {
  @Input() subGroup!: SubGroup;

}
