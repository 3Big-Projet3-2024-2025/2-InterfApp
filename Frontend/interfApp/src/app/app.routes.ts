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
import { notAuthGuard } from './guard/not-auth.guard';
import {ProfilePageComponent} from "./pages/profile-page/profile-page.component";

export const routes: Routes = [
    { path: 'createform', component: CreateFormComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    { path: 'forms', component: OpenFormComponent , canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: 'form/:id', component:  ReplyFormComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
    { path: 'profile', component:  ProfilePageComponent, canActivate: [authGuardGuard, roleGuardGuard], data: { role: 'User' }},
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
