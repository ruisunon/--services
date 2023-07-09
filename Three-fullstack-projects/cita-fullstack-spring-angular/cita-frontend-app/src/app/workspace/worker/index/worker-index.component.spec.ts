import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkerIndexComponent } from './worker-index.component';

describe('WorkerIndexComponent', () => {
  let component: WorkerIndexComponent;
  let fixture: ComponentFixture<WorkerIndexComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WorkerIndexComponent]
    });
    fixture = TestBed.createComponent(WorkerIndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
