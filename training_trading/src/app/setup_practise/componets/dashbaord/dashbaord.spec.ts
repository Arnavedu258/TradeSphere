import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Dashbaord } from './dashbaord';

describe('Dashbaord', () => {
  let component: Dashbaord;
  let fixture: ComponentFixture<Dashbaord>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Dashbaord],
    }).compileComponents();

    fixture = TestBed.createComponent(Dashbaord);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
