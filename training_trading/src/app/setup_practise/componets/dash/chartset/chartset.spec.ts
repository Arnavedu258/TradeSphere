import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Chartset } from './chartset';

describe('Chartset', () => {
  let component: Chartset;
  let fixture: ComponentFixture<Chartset>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Chartset],
    }).compileComponents();

    fixture = TestBed.createComponent(Chartset);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
