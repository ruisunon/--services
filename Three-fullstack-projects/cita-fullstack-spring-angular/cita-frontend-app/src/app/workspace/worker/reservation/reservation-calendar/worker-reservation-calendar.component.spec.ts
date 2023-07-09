import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkerReservationCalendarComponent } from './worker-reservation-calendar.component';

describe('WorkerReservationCalendarComponent', () => {
  let component: WorkerReservationCalendarComponent;
  let fixture: ComponentFixture<WorkerReservationCalendarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WorkerReservationCalendarComponent]
    });
    fixture = TestBed.createComponent(WorkerReservationCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
