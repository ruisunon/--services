import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AboutComponent } from './component/about/about.component';
import { ContactComponent } from './component/contact/contact.component';
import { HomeComponent } from './component/home/home.component';
import { AuthenticationComponent } from './component/authentication/authentication.component';
import { RegistrationComponent } from './component/registration/registration.component';
import { NavbarComponent } from './component/navbar/navbar.component';

import { CustomerReservationComponent } from './workspace/customer/reservation/customer-reservation.component';
import { CustomerRatingComponent } from './workspace/customer/rating/customer-rating.component';
import { CustomerProfileComponent } from './workspace/customer/profile/customer-profile.component';
import { CustomerIndexComponent } from './workspace/customer/index/customer-index.component';
import { ManagerCategoryComponent } from './workspace/manager/category/manager-category.component';
import { ManagerIndexComponent } from './workspace/manager/index/manager-index.component';
import { ManagerWorkerComponent } from './workspace/manager/worker/manager-worker.component';
import { ManagerProfileComponent } from './workspace/manager/profile/manager-profile.component';
import { ManagerReservationComponent } from './workspace/manager/reservation/manager-reservation.component';
import { ManagerServiceDetailComponent } from './workspace/manager/service-detail/manager-service-detail.component';
import { OwnerIndexComponent } from './workspace/owner/index/owner-index.component';
import { WorkerIndexComponent } from './workspace/worker/index/worker-index.component';
import { WorkerProfileComponent } from './workspace/worker/profile/worker-profile.component';
import { WorkerReservationComponent } from './workspace/worker/reservation/worker-reservation.component';
import { LocationComponent } from './component/location/location.component';
import { OwnerProfileComponent } from './workspace/owner/profile/owner-profile.component';
import { SaloonComponent } from './component/saloon/saloon.component';
import { WorkspaceComponent } from './workspace/workspace.component';
import { SaloonCalendarComponent } from './component/saloon/saloon-calendar/saloon-calendar.component';
import { SaloonDetailComponent } from './component/saloon/saloon-detail/saloon-detail.component';
import { CustomerAssignedWorkerComponent } from './workspace/customer/reservation/customer-assigned-worker/customer-assigned-worker.component';
import { CustomerReservationDetailsComponent } from './workspace/customer/reservation/customer-reservation-details/customer-reservation-details.component';
import { ManagerReservationDetailsComponent } from './workspace/manager/reservation/manager-reservation-details/manager-reservation-details.component';
import { ManagerReservationCalendarComponent } from './workspace/manager/reservation/reservation-calendar/manager-reservation-calendar.component';
import { WorkerReservationCalendarComponent } from './workspace/worker/reservation/reservation-calendar/worker-reservation-calendar.component';
import { WorkerReservationDetailsComponent } from './workspace/worker/reservation/reservation-details/worker-reservation-details.component';
import { ManagerWorkerAssignmentComponent } from './workspace/manager/worker/manager-worker-assignment/manager-worker-assignment.component';
import { CustomerFavouriteComponent } from './workspace/customer/customer-favourite/customer-favourite.component';
import { FullCalendarModule } from '@fullcalendar/angular';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ToastNoAnimationModule, ToastrModule, ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '@service/authentication.service';
import { TagService } from '@service/tag.service';
import { RegistrationService } from '@service/registration.service';
import { CredentialService } from '@service/credential.service';
import { CustomerService } from '@service/customer.service';
import { ManagerCategoryService } from '@service/employee/manager/manager-category.service';
import { ManagerReservationDetailService } from '@service/employee/manager/manager-reservation-detail.service';
import { ManagerServiceDetailService } from '@service/employee/manager/manager-service-detail.service';
import { CustomerProfileService } from '@service/customer/customer-profile.service';
import { CustomerFavouriteService } from '@service/customer/customer-favourite.service';
import { CustomerReservationService } from '@service/customer/customer-reservation.service';
import { CustomerReservationDetailService } from '@service/customer/customer-reservation-detail.service';
import { ErrorHandlerService } from '@service/error-handler.service';
import { ReservationService } from '@service/reservation.service';
import { SaloonService } from '@service/saloon.service';
import { DatePipe } from '@angular/common';
import { TaskService } from '@service/task.service';
import { ServiceDetailService } from '@service/service-detail.service';
import { OrderedDetailService } from '@service/ordered-detail.service';
import { HealthLivenessService } from '@service/health-liveness.service';
import { EmployeeService } from '@service/employee.service';
import { NotificationService } from '@service/notification.service';
import { WorkerReservationService } from '@service/employee/worker/worker-reservation.service';
import { WorkerReservationDetailService } from '@service/employee/worker/worker-reservation-detail.service';
import { WorkerProfileService } from '@service/employee/worker/worker-profile.service';
import { WorkerReservationTaskService } from '@service/employee/worker/worker-reservation-task.service';
import { ManagerProfileService } from '@service/employee/manager/manager-profile.service';


@NgModule({
  declarations: [
    AppComponent,
    AboutComponent,
    ContactComponent,
    HomeComponent,
    AuthenticationComponent,
    RegistrationComponent,
    NavbarComponent,
    LocationComponent,
   
    SaloonComponent,
    SaloonCalendarComponent,
    SaloonDetailComponent,

    CustomerReservationComponent,
    CustomerRatingComponent,
    CustomerProfileComponent,
    CustomerIndexComponent,
    CustomerAssignedWorkerComponent,
    CustomerReservationDetailsComponent,
    CustomerFavouriteComponent,

    WorkspaceComponent,
    WorkerIndexComponent,
    WorkerProfileComponent,
    WorkerReservationComponent,
    WorkerReservationCalendarComponent,
    WorkerReservationDetailsComponent,

    ManagerCategoryComponent,
    ManagerIndexComponent,
    ManagerWorkerComponent,
    ManagerProfileComponent,
    ManagerReservationComponent,
    ManagerServiceDetailComponent,
    ManagerReservationDetailsComponent,
    ManagerWorkerAssignmentComponent,
    ManagerReservationCalendarComponent,

    OwnerIndexComponent,
    OwnerProfileComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    ToastrModule.forRoot(),
    ToastNoAnimationModule.forRoot(),
    FullCalendarModule
  ],
  providers: [
    AuthenticationService,
    RegistrationService,
    TagService,
    CredentialService,
    CustomerService,
    CustomerProfileService,
    CustomerFavouriteService,
    CustomerReservationService,
    CustomerReservationDetailService,
    ErrorHandlerService,
    ReservationService,
    SaloonService,
    DatePipe,
    TaskService,
    ServiceDetailService,
    OrderedDetailService,
    HealthLivenessService,
    EmployeeService,
    ToastrService,
    NotificationService,
    WorkerReservationService,
    WorkerReservationDetailService,
    WorkerProfileService,
    WorkerReservationTaskService,
    ManagerProfileService,
    ManagerReservationDetailService,
    ManagerServiceDetailService,
    ManagerCategoryService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
