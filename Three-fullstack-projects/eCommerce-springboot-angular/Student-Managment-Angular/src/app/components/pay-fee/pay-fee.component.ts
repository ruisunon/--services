import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FeeService } from 'src/app/services/fee-service/fee.service';

@Component({
  selector: 'app-pay-fee',
  templateUrl: './pay-fee.component.html',
  styleUrls: ['./pay-fee.component.css']
})
export class PayFeeComponent {

  studentId: any = this.activatedRoute.snapshot.params["studentId"]; F
  validateForm: FormGroup;

  constructor(private feeService: FeeService,
    private fb: FormBuilder,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.validateForm = this.fb.group({
      amount: [null, [Validators.required]]
    })
  }

  payFee() {
    this.feeService.payFee(this.studentId, this.validateForm.value).subscribe((res) => {
      console.log(res);
    })
  }

}
