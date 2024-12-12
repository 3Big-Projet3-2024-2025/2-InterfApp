import { Routes } from '@angular/router';
import {GroupPageComponent} from "./pages/group-page/group-page.component";
import {ModifyGroupComponent} from "./components/modify-group/modify-group.component";
import {CreateGroupPageComponent} from "./pages/create-group-page/create-group-page.component";

export const routes: Routes = [
  {
    path: 'group', component: GroupPageComponent,
    children: [
      { path: 'modify/:id', component: ModifyGroupComponent }

    ]
  },
  { path: 'create-group', component: CreateGroupPageComponent }
];
