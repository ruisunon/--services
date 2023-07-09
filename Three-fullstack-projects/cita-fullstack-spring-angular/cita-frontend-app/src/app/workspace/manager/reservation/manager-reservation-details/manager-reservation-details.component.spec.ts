import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerReservationDetailsComponent } from './manager-reservation-details.component';

describe('ManagerReservationDetailsComponent', () => {
  let component: ManagerReservationDetailsComponent;
  let fixture: ComponentFixture<ManagerReservationDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagerReservationDetailsComponent]
    });
    fixture = TestBed.createComponent(ManagerReservationDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
