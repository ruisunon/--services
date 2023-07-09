import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerReservationComponent } from './manager-reservation.component';

describe('ManagerReservationComponent', () => {
  let component: ManagerReservationComponent;
  let fixture: ComponentFixture<ManagerReservationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagerReservationComponent]
    });
    fixture = TestBed.createComponent(ManagerReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
