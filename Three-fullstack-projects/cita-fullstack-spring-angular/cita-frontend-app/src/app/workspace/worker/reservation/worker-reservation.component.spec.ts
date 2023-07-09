import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkerReservationComponent } from './worker-reservation.component';

describe('WorkerReservationComponent', () => {
  let component: WorkerReservationComponent;
  let fixture: ComponentFixture<WorkerReservationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WorkerReservationComponent]
    });
    fixture = TestBed.createComponent(WorkerReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
