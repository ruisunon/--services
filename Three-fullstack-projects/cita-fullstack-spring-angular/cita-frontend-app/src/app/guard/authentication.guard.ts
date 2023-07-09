
import { Injectable, inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';


export function AuthenticationGuard(): CanActivateFn {
  
  return () => {

    const oauthService: AuthenticationService = inject(AuthenticationService);

    return !oauthService.isLoggedIn();
  }
  
}
