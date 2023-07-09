import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaloonCalendarComponent } from './saloon-calendar.component';

describe('SaloonCalendarComponent', () => {
  let component: SaloonCalendarComponent;
  let fixture: ComponentFixture<SaloonCalendarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SaloonCalendarComponent]
    });
    fixture = TestBed.createComponent(SaloonCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
