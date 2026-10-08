import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Paramdash } from './paramdash';

describe('Paramdash', () => {
  let component: Paramdash;
  let fixture: ComponentFixture<Paramdash>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Paramdash],
    }).compileComponents();

    fixture = TestBed.createComponent(Paramdash);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
