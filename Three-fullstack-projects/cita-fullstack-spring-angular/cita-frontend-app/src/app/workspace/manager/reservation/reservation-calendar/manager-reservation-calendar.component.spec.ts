import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerReservationCalendarComponent } from './manager-reservation-calendar.component';

describe('ManagerReservationCalendarComponent', () => {
  let component: ManagerReservationCalendarComponent;
  let fixture: ComponentFixture<ManagerReservationCalendarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagerReservationCalendarComponent]
    });
    fixture = TestBed.createComponent(ManagerReservationCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
