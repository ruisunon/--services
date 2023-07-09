import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaloonDetailComponent } from './saloon-detail.component';

describe('SaloonDetailComponent', () => {
  let component: SaloonDetailComponent;
  let fixture: ComponentFixture<SaloonDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SaloonDetailComponent]
    });
    fixture = TestBed.createComponent(SaloonDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
