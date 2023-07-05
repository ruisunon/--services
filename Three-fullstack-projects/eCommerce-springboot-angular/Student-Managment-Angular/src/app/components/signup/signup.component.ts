import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth-service/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  validateForm: FormGroup;

  confirmPassword = (control: FormControl): { [s: string]: boolean } => {
    if (!control.value) {
      return { required: true };
    } else if (control.value !== this.validateForm.controls["password"].value) {
      return {
        confirm: true, error: true
      };
    }
    return {};
  }

  constructor(private authService: AuthService,
    private fb: FormBuilder) { }


  ngOnInit() {
    this.validateForm = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      name: [null, [Validators.required]],
      password: [null, [Validators.required]],
      confirm: [null, [this.confirmPassword, Validators.required]]
    })
  }

  signUp(data) {
    this.authService.register(data).subscribe((res) => {
      console.log(res);
    })
  }


}
