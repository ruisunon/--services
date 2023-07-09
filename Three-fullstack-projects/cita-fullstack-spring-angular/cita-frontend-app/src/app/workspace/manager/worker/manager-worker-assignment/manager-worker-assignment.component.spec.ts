import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerWorkerAssignmentComponent } from './manager-worker-assignment.component';

describe('ManagerWorkerAssignmentComponent', () => {
  let component: ManagerWorkerAssignmentComponent;
  let fixture: ComponentFixture<ManagerWorkerAssignmentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagerWorkerAssignmentComponent]
    });
    fixture = TestBed.createComponent(ManagerWorkerAssignmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
