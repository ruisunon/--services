import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerAssignedWorkerComponent } from './customer-assigned-worker.component';

describe('CustomerAssignedWorkerComponent', () => {
  let component: CustomerAssignedWorkerComponent;
  let fixture: ComponentFixture<CustomerAssignedWorkerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CustomerAssignedWorkerComponent]
    });
    fixture = TestBed.createComponent(CustomerAssignedWorkerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
