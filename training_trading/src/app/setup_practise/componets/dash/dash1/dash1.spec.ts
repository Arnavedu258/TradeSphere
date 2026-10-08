import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Dash1 } from './dash1';

describe('Dash1', () => {
  let component: Dash1;
  let fixture: ComponentFixture<Dash1>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Dash1],
    }).compileComponents();

    fixture = TestBed.createComponent(Dash1);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
