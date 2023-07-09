
import { Injectable, inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, RouterStateSnapshot } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';


export function RegistrationGuard(): CanActivateFn  {
 
  return () => {
    const oauthService: AuthenticationService = inject(AuthenticationService);
    return  !oauthService.isLoggedIn();
  }
  
}
  