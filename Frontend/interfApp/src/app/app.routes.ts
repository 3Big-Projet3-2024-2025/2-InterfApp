import { Routes } from '@angular/router';
import { GroupPageComponent } from "./pages/group-page/group-page.component";
import { ModifyGroupComponent } from "./components/modify-group/modify-group.component";
import { CreateGroupPageComponent } from "./pages/create-group-page/create-group-page.component";
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
import { SummaryAnswersComponent } from './pages/summary-answers/summary-answers.component';
import { ModifFormPageComponent } from './pages/modif-form-page/modif-form-page.component';
import { AdminHomeComponent } from './pages/admin-home/admin-home.component';
import { AdminUserComponent } from './components/admin-user/admin-user.component';
import { AdminGroupComponent } from './components/admin-group/admin-group.component';
import { AdminEditUserComponent } from './components/admin-edit-user/admin-edit-user.component';

export const routes: Routes = [
  // Redirection vers login
  { path: '', redirectTo: '/login', pathMatch: 'full' },

  // Authentification et inscription
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent, canActivate: [notAuthGuard] },

  // Pages utilisateur
  { path: 'createform/:id', component: CreateFormComponent, canActivate: [authGuardGuard], data: { role: 'User' } },
  { path: 'forms', component: OpenFormComponent, canActivate: [authGuardGuard], data: { role: 'User' } },
  { path: 'form/:id', component: ReplyFormComponent, canActivate: [authGuardGuard], data: { role: 'User' } },
  { path: 'form-modif/:id', component: ModifFormPageComponent, canActivate: [authGuardGuard], data: { role: 'User' } },
  { path: 'answer/:id', component: SummaryAnswersComponent, canActivate: [authGuardGuard], data: { role: 'User' } },
  { path: 'profil', component: ProfilComponent, canActivate: [authGuardGuard], data: { role: 'User' } },

  // Pages administrateur
  { path: 'admin', component: AdminHomeComponent, canActivate: [authGuardGuard], data: { role: 'Admin' } },
  { path: 'admin/users', component: AdminUserComponent, canActivate: [authGuardGuard], data: { role: 'Admin' } },
  { path: 'admin/users/edit/:id', component: AdminEditUserComponent, canActivate: [authGuardGuard], data: { role: 'Admin' } }, // Nouvelle route pour modifier un utilisateur
  { path: 'admin/groups', component: AdminGroupComponent, canActivate: [authGuardGuard], data: { role: 'Admin' } },

  // Gestion des groupes
  { path: 'group', component: ListGroupPageComponent,
    children: [
      { path: 'modify/:id', component: ModifyGroupComponent }
              ] },
  { path: 'group/:id', component: GroupPageComponent },
  { path: 'create-group', component: CreateGroupPageComponent },

  // Gestion des groupes (liste)
  { path: 'groups/list', component: ListGroupPageComponent, canActivate: [authGuardGuard], data: { role: 'User' } },
];
