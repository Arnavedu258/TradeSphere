import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Dash2 } from './dash2';

describe('Dash2', () => {
  let component: Dash2;
  let fixture: ComponentFixture<Dash2>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Dash2],
    }).compileComponents();

    fixture = TestBed.createComponent(Dash2);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
