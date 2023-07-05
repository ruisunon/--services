import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(private router: Router) { }

  isUserLoggedIn: boolean = UserStorageService.isUserLoggedIn();

  ngOnInit() {
    this.router.events.subscribe(event => {
      if (event.constructor.name == "NavigationEnd") {
        this.isUserLoggedIn = UserStorageService.isUserLoggedIn();
      }
    })
  }


  logout() {
    UserStorageService.signOut();
    this.router.navigateByUrl("login")
  }

}
