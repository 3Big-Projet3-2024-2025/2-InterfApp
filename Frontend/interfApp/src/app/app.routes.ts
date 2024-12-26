import { Routes } from '@angular/router';
import { CreateFormComponent } from './create-form/create-form.component';
import { OpenFormComponent } from './open-form/open-form.component';
import { ReplyFormComponent } from './reply-form/reply-form.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { authGuardGuard } from './guard/auth-guard.guard';
import { roleGuardGuard } from './guard/role-guard.guard';
import { ProfilComponent } from './profil/profil.component';
import { notAuthGuard } from './guard/not-auth.guard';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminUserComponent } from './admin-user/admin-user.component';
import { AdminGroupComponent } from './admin-group/admin-group.component';

export const routes: Routes = [
    { path: 'createform', component: CreateFormComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'forms', component: OpenFormComponent , canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: 'form/:id', component:  ReplyFormComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: 'profil', component:  ProfilComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: 'register', component:  RegisterComponent},
    { path: 'login', component:  LoginComponent, canActivate : [notAuthGuard ] },
    { path: 'admin', component: AdminHomeComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'ADMIN' }},
    { path: 'admin/users', component: AdminUserComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'ADMIN' }},
    { path: 'admin/groups', component: AdminGroupComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'ADMIN' }},
];
