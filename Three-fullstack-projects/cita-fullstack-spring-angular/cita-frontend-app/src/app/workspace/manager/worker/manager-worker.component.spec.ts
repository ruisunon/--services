import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerWorkerComponent } from './manager-worker.component';

describe('ManagerWorkerComponent', () => {
  let component: ManagerWorkerComponent;
  let fixture: ComponentFixture<ManagerWorkerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagerWorkerComponent]
    });
    fixture = TestBed.createComponent(ManagerWorkerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
