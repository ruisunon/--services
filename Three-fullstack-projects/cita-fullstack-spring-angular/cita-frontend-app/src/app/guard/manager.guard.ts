
import { Injectable, inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateChild, CanActivateChildFn, RouterStateSnapshot, UrlTree } from '@angular/router';
import { UserRoleBasedAuthority } from '../model/user-role-based-authority';
import { AuthenticationService } from '../service/authentication.service';


  export function ManagerGuard(): CanActivateChildFn {
  
    return () => {
  
      const oauthService: AuthenticationService = inject(AuthenticationService);
        
      const isUserLoggedIn: boolean = oauthService.isLoggedIn();
      
      if (isUserLoggedIn) {
        const userRole: string = `${sessionStorage.getItem(`userRole`)}`;
        return userRole === UserRoleBasedAuthority.MANAGER
        || userRole === UserRoleBasedAuthority.OWNER;
      }
      else
        return false;
    }
    
  }
  
