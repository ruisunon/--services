
import { Injectable, inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthenticationService } from '../service/authentication.service';


export function SaloonDetailGuard(): CanActivateFn {
  return () => {
    const authenticationService: AuthenticationService = inject(AuthenticationService);
    const router: Router = inject(Router);
    const isLoggedIn: boolean = authenticationService.isLoggedIn();
    if (!isLoggedIn)
      router.navigateByUrl(`/login`);
    return isLoggedIn;
  };


}








