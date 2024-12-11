import { Routes } from '@angular/router';
import { CreateFormComponent } from './create-form/create-form.component';
import { OpenFormComponent } from './open-form/open-form.component';
import { ReplyFormComponent } from './reply-form/reply-form.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { authGuardGuard } from './guard/auth-guard.guard';
import { roleGuardGuard } from './guard/role-guard.guard';
import { ProfilComponent } from './profil/profil.component';

export const routes: Routes = [
    { path: 'createform', component: CreateFormComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'ROLE_User' }},
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'forms', component: OpenFormComponent , canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'ROLE_User' }},
    { path: 'form/:id', component:  ReplyFormComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'ROLE_User' }},
    { path: 'profil', component:  ProfilComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'ROLE_User' }},
    { path: 'register', component:  RegisterComponent},
    { path: 'login', component:  LoginComponent, canDeactivate : [authGuardGuard]},
];
