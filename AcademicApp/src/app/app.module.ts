import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { StudentDashboardComponent } from './student-dashboard/student-dashboard.component';
import { TeacherDashboardComponent } from './teacher-dashboard/teacher-dashboard.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { TestingDashboardComponent } from './testing-dashboard/testing-dashboard.component';
import { StudentMenuComponent } from './components/menus/student-menu/student-menu.component';
import { TeacherMenuComponent } from './components/menus/teacher-menu/teacher-menu.component';
import { AdminMenuComponent } from './components/menus/admin-menu/admin-menu.component';
import { ProfilePageComponent } from './components/pages/profile-page/profile-page.component';
import { DocumentsPageComponent } from './components/pages/documents-page/documents-page.component';
import { CurriculumGradesPageComponent } from './components/pages/curriculum-grades-page/curriculum-grades-page.component';
import { ProposedOptionalsPageComponent } from './components/pages/proposed-optionals-page/proposed-optionals-page.component';
import { EnrollComponent } from './components/pages/enroll/enroll.component';
import { AddGradeComponent } from './components/pages/add-grade/add-grade.component';
import { ApproveOptionalsComponent } from './components/pages/approve-optionals/approve-optionals.component';
import { StatisticsComponent } from './components/pages/statistics/statistics.component';
import { AskPrintComponent } from './components/pages/ask-print/ask-print.component';
import { ClassementGrantsComponent } from './components/pages/classement-grants/classement-grants.component';
import { BodyComponent } from './components/pages/body/body.component';
import { HomeComponent } from './components/pages/home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    StudentDashboardComponent,
    LoginComponent,
    TeacherDashboardComponent,
    AdminDashboardComponent,
    TestingDashboardComponent,
    StudentMenuComponent,
    TeacherMenuComponent,
    AdminMenuComponent,
    ProfilePageComponent,
    DocumentsPageComponent,
    CurriculumGradesPageComponent,
    ProposedOptionalsPageComponent,
    EnrollComponent,
    AddGradeComponent,
    ApproveOptionalsComponent,
    StatisticsComponent,
    AskPrintComponent,
    ClassementGrantsComponent,
    BodyComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
