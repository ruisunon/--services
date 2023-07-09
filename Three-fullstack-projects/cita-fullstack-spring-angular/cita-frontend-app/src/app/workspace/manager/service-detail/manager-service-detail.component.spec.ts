import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManagerServiceDetailComponent } from './manager-service-detail.component';

describe('ManagerServiceDetailComponent', () => {
  let component: ManagerServiceDetailComponent;
  let fixture: ComponentFixture<ManagerServiceDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ManagerServiceDetailComponent]
    });
    fixture = TestBed.createComponent(ManagerServiceDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
