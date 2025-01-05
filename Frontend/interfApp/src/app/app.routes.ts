import { Routes } from '@angular/router';
import { GroupPageComponent } from "./pages/group-page/group-page.component";
import { ModifyGroupComponent } from "./components/modify-group/modify-group.component";
import { CreateGroupPageComponent } from "./pages/create-group-page/create-group-page.component";
import { CreateFormComponent } from './pages/create-form/create-form.component';
import { ReplyFormComponent } from './pages/reply-form/reply-form.component';
import { RegisterComponent } from './pages/register/register.component';
import { authGuardGuard } from './guard/auth-guard.guard';
import { roleGuardGuard } from './guard/role-guard.guard';
import { notAuthGuard } from './guard/not-auth.guard';
import { ProfilePageComponent} from "./pages/profile-page/profile-page.component";
import { ListGroupPageComponent } from './pages/list-group-page/list-group-page.component';
import { SummaryAnswersComponent } from './pages/summary-answers/summary-answers.component';
import { ModifFormPageComponent } from './pages/modif-form-page/modif-form-page.component';
import { AdminHomeComponent } from './pages/admin-home/admin-home.component';
import { AdminUserComponent } from './components/admin-user/admin-user.component';
import { AdminGroupComponent } from './components/admin-group/admin-group.component';
import { AdminEditUserComponent } from './components/admin-edit-user/admin-edit-user.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';

export const routes: Routes = [
  // if the path is empty, redirection to home
  { path: '', redirectTo: '/home', pathMatch: 'full' },

  // authantification and register
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent, canActivate: [notAuthGuard] },

  // users pages
  { path: 'createform/:id', component: CreateFormComponent, canActivate: [authGuardGuard], data: { role: 'User' } },
  { path: 'form/:id', component: ReplyFormComponent, canActivate: [authGuardGuard], data: { role: 'User' } },
  { path: 'form-modif/:id', component: ModifFormPageComponent, canActivate: [authGuardGuard], data: { role: 'User' } },
  { path: 'answer/:id', component: SummaryAnswersComponent, canActivate: [authGuardGuard], data: { role: 'User' } },
  { path: 'profil', component: ProfilePageComponent, canActivate: [authGuardGuard], data: { role: 'User' } },
  { path: 'home', component: HomeComponent},

  // admin pages
  { path: 'admin', component: AdminHomeComponent, canActivate: [authGuardGuard], data: { role: 'Admin' } },
  { path: 'admin/users', component: AdminUserComponent, canActivate: [authGuardGuard], data: { role: 'Admin' } },
  { path: 'admin/users/edit/:id', component: AdminEditUserComponent, canActivate: [authGuardGuard], data: { role: 'Admin' } },
  { path: 'admin/groups', component: AdminGroupComponent, canActivate: [authGuardGuard], data: { role: 'Admin' } },

  // group manager pages
  { path: 'group', component: ListGroupPageComponent,  canActivate: [authGuardGuard], data: { role: 'User' }},
  { path: 'group/:id', component: GroupPageComponent,  canActivate: [authGuardGuard], data: { role: 'User' }},
  { path: 'create-group', component: CreateGroupPageComponent,  canActivate: [authGuardGuard], data: { role: 'User' } },

  // management groups (list)
  { path: 'groups/list', component: ListGroupPageComponent, canActivate: [authGuardGuard], data: { role: 'User' } },

  // if there's a error, redirection to home, it's the route by default 
  {path:'**', redirectTo:'/home'},
];
