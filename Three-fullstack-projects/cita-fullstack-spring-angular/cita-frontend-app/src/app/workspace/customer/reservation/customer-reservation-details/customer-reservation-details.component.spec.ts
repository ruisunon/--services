import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerReservationDetailsComponent } from './customer-reservation-details.component';

describe('CustomerReservationDetailsComponent', () => {
  let component: CustomerReservationDetailsComponent;
  let fixture: ComponentFixture<CustomerReservationDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CustomerReservationDetailsComponent]
    });
    fixture = TestBed.createComponent(CustomerReservationDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
