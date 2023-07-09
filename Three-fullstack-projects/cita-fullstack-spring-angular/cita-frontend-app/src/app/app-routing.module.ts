
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from './component/about/about.component';
import { AuthenticationComponent } from './component/authentication/authentication.component';
import { ContactComponent } from './component/contact/contact.component';
import { Error404Component } from './component/error/error404/error404.component';
import { HomeComponent } from './component/home/home.component';

import { RegistrationComponent } from './component/registration/registration.component';

import { CustomerGuard } from './guard/customer.guard';
import { WorkerGuard } from './guard/worker.guard';
import { ManagerGuard } from './guard/manager.guard';
import { OwnerGuard } from './guard/owner.guard';
import { AuthenticationGuard } from './guard/authentication.guard';
import { RegistrationGuard } from './guard/registration.guard';
import { SaloonComponent } from './component/saloon/saloon.component';
import { LocationComponent } from './component/location/location.component';
import { SaloonDetailComponent } from './component/saloon/saloon-detail/saloon-detail.component';
import { SaloonDetailGuard } from './guard/saloon-detail.guard';
import { SaloonCalendarComponent } from './component/saloon/saloon-calendar/saloon-calendar.component';
import { LogoutComponent } from './component/logout/logout.component';
import { CustomerIndexComponent } from './workspace/customer/index/customer-index.component';
import { CustomerProfileComponent } from './workspace/customer/profile/customer-profile.component';
import { CustomerRatingComponent } from './workspace/customer/rating/customer-rating.component';
import { CustomerReservationComponent } from './workspace/customer/reservation/customer-reservation.component';
import { ManagerCategoryComponent } from './workspace/manager/category/manager-category.component';
import { ManagerIndexComponent } from './workspace/manager/index/manager-index.component';
import { ManagerProfileComponent } from './workspace/manager/profile/manager-profile.component';
import { ManagerReservationComponent } from './workspace/manager/reservation/manager-reservation.component';
import { ManagerServiceDetailComponent } from './workspace/manager/service-detail/manager-service-detail.component';
import { ManagerWorkerComponent } from './workspace/manager/worker/manager-worker.component';
import { OwnerIndexComponent } from './workspace/owner/index/owner-index.component';
import { OwnerProfileComponent } from './workspace/owner/profile/owner-profile.component';
import { WorkerIndexComponent } from './workspace/worker/index/worker-index.component';
import { WorkerProfileComponent } from './workspace/worker/profile/worker-profile.component';
import { WorkerReservationComponent } from './workspace/worker/reservation/worker-reservation.component';
import { CustomerAssignedWorkerComponent } from '@customer/reservation/customer-assigned-worker/customer-assigned-worker.component';
import { ManagerReservationCalendarComponent } from '@manager/reservation/reservation-calendar/manager-reservation-calendar.component';
import { ManagerReservationDetailsComponent } from '@manager/reservation/manager-reservation-details/manager-reservation-details.component';
import { ManagerWorkerAssignmentComponent } from '@manager/worker/manager-worker-assignment/manager-worker-assignment.component';
import { WorkerReservationCalendarComponent } from '@worker/reservation/reservation-calendar/worker-reservation-calendar.component';
import { WorkerReservationDetailsComponent } from '@worker/reservation/reservation-details/worker-reservation-details.component';
import { CustomerFavouriteComponent } from '@customer/customer-favourite/customer-favourite.component';


const routes: Routes = [
  
  {
    path: "", 
    children: [
      
      { path: "", component: HomeComponent },
      { path: "home", redirectTo: "" },
      { path: "about", component: AboutComponent },
      { path: "contact", component: ContactComponent },
      { path: "locations", component: LocationComponent },
      
      { path: "locations/:state/saloons", component: SaloonComponent },
      
      { path: "saloons", component: SaloonComponent },
      { path: "saloons/:id", component: SaloonDetailComponent, canActivate: [SaloonDetailGuard] },
      { path: "saloons/:identifier", component: SaloonDetailComponent, canActivate: [SaloonDetailGuard] }, // ************
      { path: "saloons/:id/calendar", component: SaloonCalendarComponent, canActivate: [SaloonDetailGuard] },
      { path: "saloons/:identifier/calendar", component: SaloonCalendarComponent, canActivate: [SaloonDetailGuard] }, // ************
      
      { path: "logout", component: LogoutComponent },
      { path: "authenticate", component: AuthenticationComponent, canActivate: [AuthenticationGuard] },
      { path: "login", redirectTo: "authenticate" },
      { path: "register", component: RegistrationComponent, canActivate: [RegistrationGuard] },

      {
        path: "workspace/customer",
        canActivateChild: [CustomerGuard],
        children: [
          { path: "", component: CustomerIndexComponent },
          { path: "index", redirectTo: "" },
          { path: "profile", component: CustomerProfileComponent },
          { path: "reservations", component: CustomerReservationComponent },
          { path: "reservations/:reservationId", component: CustomerReservationComponent },
          { path: "reservations/:reservationIdentifier", component: CustomerReservationComponent }, // ************
          { path: "reservations/assigned/:workerId", component: CustomerAssignedWorkerComponent },
          { path: "reservations/assigned/:workerIdentifier", component: CustomerAssignedWorkerComponent }, // ************
          { path: "favourites", component: CustomerFavouriteComponent },
          { path: "ratings", component: CustomerRatingComponent },
        ]
      },
      {
        path: "workspace/worker",
        canActivateChild: [WorkerGuard],
        children: [
          { path: "", component: WorkerIndexComponent },
          { path: "index", redirectTo: "" },
          { path: "profile", component: WorkerProfileComponent },
          { path: "reservations", component: WorkerReservationComponent },
          { path: "reservations/calendar", component: WorkerReservationCalendarComponent },
          { path: "reservations/:reservationId", component: WorkerReservationDetailsComponent },
          { path: "reservations/:reservationIdentifier", component: WorkerReservationDetailsComponent }, // ************
        ]
      },
      {
        path: "workspace/manager",
        canActivateChild: [WorkerGuard, ManagerGuard],
        children: [
          { path: "", component: ManagerIndexComponent },
          { path: "index", redirectTo: "" },
          { path: "profile", component: ManagerProfileComponent },
          { path: "reservations", component: ManagerReservationComponent },
          { path: "reservations/calendar", component: ManagerReservationCalendarComponent },
          { path: "reservations/:reservationId", component: ManagerReservationComponent },
          { path: "reservations/:reservationIdentifier", component: ManagerReservationDetailsComponent }, // ************
          { path: "workers", component: ManagerWorkerComponent },
          { path: "workers/assignments/:workerId", component: ManagerWorkerAssignmentComponent },
          { path: "workers/assignments/:workerIdentifier", component: ManagerWorkerAssignmentComponent }, // ************
          { path: "categories", component: ManagerCategoryComponent },
          { path: "services", component: ManagerServiceDetailComponent },
        ]
      },
      {
        path: "workspace/owner",
        canActivateChild: [WorkerGuard, ManagerGuard, OwnerGuard],
        children: [
          { path: "", component: OwnerIndexComponent },
          { path: "index", redirectTo: "" },
          { path: "profile", component: OwnerProfileComponent },
        ]
      },

    ]
  },
  
  { path: "**", component: Error404Component },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
