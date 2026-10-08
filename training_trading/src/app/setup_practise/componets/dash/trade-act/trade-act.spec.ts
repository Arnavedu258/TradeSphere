import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TradeAct } from './trade-act';

describe('TradeAct', () => {
  let component: TradeAct;
  let fixture: ComponentFixture<TradeAct>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TradeAct],
    }).compileComponents();

    fixture = TestBed.createComponent(TradeAct);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
