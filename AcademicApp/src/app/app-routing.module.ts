import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { LoginComponent } from './login/login.component';
import { StudentDashboardComponent } from './student-dashboard/student-dashboard.component';
import { TeacherDashboardComponent } from './teacher-dashboard/teacher-dashboard.component';
import { TestingDashboardComponent } from './testing-dashboard/testing-dashboard.component';
import { HomeComponent } from './components/pages/home/home.component';
import { StudentMenuComponent } from './components/menus/student-menu/student-menu.component';

const routes: Routes = [
  {path:'', redirectTo:'login', pathMatch:'full'},
  {path:'login', component:LoginComponent},

  //{path:'student-dashboard', component:StudentDashboardComponent},
  {path:'student-dashboard', component:StudentDashboardComponent, 
    children: [
      {path:'home', component:HomeComponent},
    ]},
  

  {path:'teacher-dashboard', component:TeacherDashboardComponent},
  {path:'staff-dashboard', component:AdminDashboardComponent},
  {path:'testing', component: TestingDashboardComponent},
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
