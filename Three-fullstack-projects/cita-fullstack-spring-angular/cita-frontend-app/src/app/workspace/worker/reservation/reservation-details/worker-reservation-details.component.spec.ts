import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkerReservationDetailsComponent } from './worker-reservation-details.component';

describe('WorkerReservationDetailsComponent', () => {
  let component: WorkerReservationDetailsComponent;
  let fixture: ComponentFixture<WorkerReservationDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WorkerReservationDetailsComponent]
    });
    fixture = TestBed.createComponent(WorkerReservationDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
