import { Routes } from '@angular/router';
import {GroupPageComponent} from "./pages/group-page/group-page.component";
import {ModifyGroupComponent} from "./components/modify-group/modify-group.component";
import {CreateGroupPageComponent} from "./pages/create-group-page/create-group-page.component";
import { CreateFormComponent } from './pages/create-form/create-form.component';
import { OpenFormComponent } from './pages/open-form/open-form.component';
import { ReplyFormComponent } from './pages/reply-form/reply-form.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { authGuardGuard } from './guard/auth-guard.guard';
import { roleGuardGuard } from './guard/role-guard.guard';
import { ProfilComponent } from './pages/profil/profil.component';
import { notAuthGuard } from './guard/not-auth.guard';
import { ListGroupPageComponent } from './pages/list-group-page/list-group-page.component';
import { AnswerPageComponent } from './pages/answer-page/answer-page.component';
import { ModifFormPageComponent } from './pages/modif-form-page/modif-form-page.component';
import { AdminHomeComponent } from './admin-home/admin-home.component';
import { AdminUserComponent } from './admin-user/admin-user.component';
import { AdminGroupComponent } from './admin-group/admin-group.component';

export const routes: Routes = [
    { path: 'createform/:id', component: CreateFormComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'forms', component: OpenFormComponent , canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: 'form/:id', component:  ReplyFormComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: 'form-modif/:id', component:  ModifFormPageComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: 'answer/:id', component:  AnswerPageComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: 'profil', component:  ProfilComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: 'register', component:  RegisterComponent},
    { path: 'login', component:  LoginComponent, canActivate : [notAuthGuard ] },
    { path: 'admin', component: AdminHomeComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'ADMIN' }},
    { path: 'admin/users', component: AdminUserComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'ADMIN' }},
    { path: 'admin/groups', component: AdminGroupComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'ADMIN' }},
];
