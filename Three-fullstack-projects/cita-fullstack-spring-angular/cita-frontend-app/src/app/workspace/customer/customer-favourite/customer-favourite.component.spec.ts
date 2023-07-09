import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomerFavouriteComponent } from './customer-favourite.component';

describe('CustomerFavouriteComponent', () => {
  let component: CustomerFavouriteComponent;
  let fixture: ComponentFixture<CustomerFavouriteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CustomerFavouriteComponent]
    });
    fixture = TestBed.createComponent(CustomerFavouriteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
