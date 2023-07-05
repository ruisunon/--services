import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  validateForm!: FormGroup;
  isSpinning = false;

  submitForm(): void {
    this.isSpinning = true;
    for (const i in this.validateForm.controls) {
      this.validateForm.controls[i].markAsDirty();
      this.validateForm.controls[i].updateValueAndValidity();
    }
    this.authService.login(this.validateForm.get(['username'])!.value, this.validateForm.get(['password'])!.value).subscribe(res => {
      this.isSpinning = false;
      if (UserStorageService.isUserLoggedIn()) {
        this.router.navigateByUrl('dashboard');
      }

      console.log("res", res);
    }, error => {
      console.log("errorr", error);

      this.isSpinning = false;

    })

  }

  constructor(private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,) { }

  ngOnInit(): void {
    this.validateForm = this.fb.group({
      username: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });
  }

}
