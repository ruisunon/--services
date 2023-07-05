import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HttpClientModule } from "@angular/common/http"
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { AddStudentComponent } from './components/add-student/add-student.component';
import { AddTeacherComponent } from './components/add-teacher/add-teacher.component';
import { UpdateStudentComponent } from './components/update-student/update-student.component';
import { PayFeeComponent } from './components/pay-fee/pay-fee.component';
import { AllTeachersComponent } from './components/all-teachers/all-teachers.component';
import { UpdateTeacherComponent } from './components/update-teacher/update-teacher.component'

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    DashboardComponent,
    NavbarComponent,
    AddStudentComponent,
    AddTeacherComponent,
    UpdateStudentComponent,
    PayFeeComponent,
    AllTeachersComponent,
    UpdateTeacherComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
