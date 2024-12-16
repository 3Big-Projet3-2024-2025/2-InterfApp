import { Routes } from '@angular/router';
import {GroupPageComponent} from "./pages/group-page/group-page.component";
import {ModifyGroupComponent} from "./components/modify-group/modify-group.component";
import {CreateGroupPageComponent} from "./pages/create-group-page/create-group-page.component";
import { CreateFormComponent } from './create-form/create-form.component';
import { OpenFormComponent } from './open-form/open-form.component';
import { ReplyFormComponent } from './reply-form/reply-form.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { authGuardGuard } from './guard/auth-guard.guard';
import { roleGuardGuard } from './guard/role-guard.guard';
import { ProfilComponent } from './profil/profil.component';
import { notAuthGuard } from './guard/not-auth.guard';

export const routes: Routes = [
    { path: 'createform', component: CreateFormComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'forms', component: OpenFormComponent , canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: 'form/:id', component:  ReplyFormComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: 'profil', component:  ProfilComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: 'register', component:  RegisterComponent},
    { path: 'login', component:  LoginComponent, canActivate : [notAuthGuard ] },
    {
      path: 'group', component: GroupPageComponent,
      children: [
        { path: 'modify/:id', component: ModifyGroupComponent }
  
      ]
    },
    { path: 'create-group', component: CreateGroupPageComponent }
];
