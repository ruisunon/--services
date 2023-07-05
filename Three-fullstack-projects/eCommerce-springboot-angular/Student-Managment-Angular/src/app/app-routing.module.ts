import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { NoauthGuard } from './gurads/noAuth/noauth.guard';
import { UserGuard } from './gurads/authUser/user.guard';
import { AddStudentComponent } from './components/add-student/add-student.component';
import { AddTeacherComponent } from './components/add-teacher/add-teacher.component';
import { UpdateStudentComponent } from './components/update-student/update-student.component';
import { PayFeeComponent } from './components/pay-fee/pay-fee.component';
import { AllTeachersComponent } from './components/all-teachers/all-teachers.component';
import { UpdateTeacherComponent } from './components/update-teacher/update-teacher.component';

const routes: Routes = [
  { path: "login", component: LoginComponent, canActivate: [NoauthGuard] },
  { path: "signup", component: SignupComponent, canActivate: [NoauthGuard] },
  { path: "dashboard", component: DashboardComponent, canActivate: [UserGuard] },
  { path: "student", component: AddStudentComponent, canActivate: [UserGuard] },
  { path: "teacher", component: AddTeacherComponent, canActivate: [UserGuard] },
  { path: "student/:studentId", component: UpdateStudentComponent, canActivate: [UserGuard] },
  { path: "fee/:studentId", component: PayFeeComponent, canActivate: [UserGuard] },
  { path: "teachers", component: AllTeachersComponent, canActivate: [UserGuard] },
  { path: "teacher/:teacherId", component: UpdateTeacherComponent, canActivate: [UserGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
